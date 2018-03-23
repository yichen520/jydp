package com.jydp.controller.wap;

import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.VO.WapUserVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * jydp wap端个人信息
 * Created by xushilong3623 on 2018/3/22.
 */

@Controller
@RequestMapping(value = "/userWap/userMine")
public class WapUserMineController {

    @Autowired
    IUserService userService;


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
}
