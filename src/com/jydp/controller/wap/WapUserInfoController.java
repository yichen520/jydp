package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.VO.WapUserCurrencyAssetsVO;
import com.jydp.entity.VO.WapUserVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IRedisService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * jydp wap端个人信息
 * Created by xushilong3623 on 2018/3/22.
 */

@Controller
@RequestMapping(value = "/userWap/userInfo")
public class WapUserInfoController {

    /**
     * 用户账号
     */
    @Autowired
    IUserService userService;

    /**
     * 用户货币数量
     */
    @Autowired
    IUserCurrencyNumService userCurrencyNumService;

    @Autowired
    IRedisService redisService;


    /**
     * 跳转到wap端个人信息界面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/show.htm", method = RequestMethod.GET)
    public String toMine(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录");
            return "page/wap/mine";
        }

        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户信息查询失败");
            return "page/wap/mine";
        }

        WapUserVO wapUserVO = new WapUserVO();
        wapUserVO.setUserId(userInfo.getUserId());
        wapUserVO.setUserAccount(userInfo.getUserAccount());
        wapUserVO.setUserBalance(userInfo.getUserBalance());
        wapUserVO.setUserBalanceLock(userInfo.getUserBalanceLock());

        request.setAttribute("code", 1);
        request.setAttribute("userInfo", wapUserVO);
        return "page/wap/mine";
    }

    /**
     * 跳转到个人中心界面
     *
     * @return
     */
    @RequestMapping(value = "/userCenter/show.htm")
    public String toUserCenter(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登陆过期");
            return "page/wap/login";
        }
        return "/page/wap/mineCenter";
    }

    /**
     * 跳转到资产币种界面
     *
     * @return
     */
    @RequestMapping(value = "/currencyAssets/show.htm")
    public String toCurrencyAssets(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登陆过期");
            return "page/wap/login";
        }
        return "/page/wap/assets";
    }

    /**
     * 获取用户币种信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/currencyAssets")
    @ResponseBody
    public JsonObjectBO getCurrencyAssets(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(4);
            response.setMessage("登陆过期");
            return response;
        }
        List<WapUserCurrencyAssetsVO> list = userCurrencyNumService.listUserCurrencyAssets(user.getUserId());
        response.setCode(1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCurrencyAssets", list);
        response.setData(jsonObject);
        return response;
    }

    /**
     * 跳转到修改支付密码页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyPayPassword/show.htm", method = RequestMethod.GET)
    public String toModifyPayPassword(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登陆过期");
            return "page/wap/login";
        }
        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户信息查询失败");
            return "/page/wap/reviseZf";
        }
        request.setAttribute("phoneAreaCode",userInfo.getPhoneAreaCode());
        request.setAttribute("phoneNumber",userInfo.getPhoneNumber());
        return "/page/wap/reviseZf";
    }

    /**
     * 跳转到修改密码页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyPassword/show.htm", method = RequestMethod.GET)
    public String toModifyPassword(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登陆过期");
            return "page/wap/login";
        }
        return "/page/wap/modifyPassword";
    }

    /**
     * 跳转到修改手机号页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyPhone/show.htm", method = RequestMethod.GET)
    public String toModifyPhone(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登陆过期");
            return "page/wap/login";
        }
        return "page/wap/modifyPhone";
    }
}
