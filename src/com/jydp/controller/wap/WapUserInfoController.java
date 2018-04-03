package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.WapUserModifyPayPasswordDTO;
import com.jydp.entity.DTO.WapUserModifyPhoneDTO;
import com.jydp.entity.VO.WapUserCurrencyAssetsVO;
import com.jydp.entity.VO.WapUserVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IRedisService;
import com.jydp.service.ISystemValidatePhoneService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * redis缓存
     */
    @Autowired
    IRedisService redisService;

    /**
     * 手机校验
     */
    @Autowired
    ISystemValidatePhoneService systemValidatePhoneService;


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
        wapUserVO.setTotalUserBalance(userInfo.getUserBalance()+userInfo.getUserBalanceLock());

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
            request.setAttribute("message", "登录过期");
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
            request.setAttribute("message", "登录过期");
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
            response.setMessage("登录过期");
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
            request.setAttribute("message", "登录过期");
            return "page/wap/login";
        }
        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户信息查询失败");
            return "/page/wap/login";
        }
        request.setAttribute("phoneAreaCode", userInfo.getPhoneAreaCode());
        request.setAttribute("phoneNumber", userInfo.getPhoneNumber());
        return "/page/wap/reviseZf";
    }

    /**
     * 通过密码修改支付密码
     *
     * @return
     */
    @RequestMapping(value = "/payPassword/modifyByPwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO modifyPayPassword(HttpServletRequest request,
                                          @RequestBody WapUserModifyPayPasswordDTO userModifyPayPasswordDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        if (userModifyPayPasswordDTO == null || !StringUtil.isNotNull(userModifyPayPasswordDTO.getOldPassword()) ||
                !StringUtil.isNotNull(userModifyPayPasswordDTO.getNewPassword()) || !StringUtil.isNotNull(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(2);
            response.setMessage("参数不能为空");
            return response;
        }
        //两次密码对比
        if (!userModifyPayPasswordDTO.getNewPassword().equals(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(3);
            response.setMessage("密码输入不一致！");
            return response;
        }
        //新密码与原密码对比
        if (userModifyPayPasswordDTO.getNewPassword().equals(userModifyPayPasswordDTO.getOldPassword())) {
            response.setCode(3);
            response.setMessage("新密码不可与原密码相同");
            return response;
        }
        //获取用户信息
        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            response.setCode(3);
            response.setMessage("用户信息查询失败，请稍后重试");
            return response;
        }
        //判断支付密码是否正确
        boolean userPay = userService.validateUserPay(user.getUserAccount(), MD5Util.toMd5(userModifyPayPasswordDTO.getOldPassword()));
        if (!userPay) {
            response.setCode(3);
            response.setMessage("原密码错误！");
            return response;
        }
        //判断支付密码是否与登录密码相同
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (userLog != null) {
            response.setCode(3);
            response.setMessage("不可与登录密码相同！");
            return response;
        }

        //修改支付密码
        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (!forgetPwd) {
            response.setCode(3);
            response.setMessage("修改失败，请重试");
            return response;
        }
        response.setCode(1);
        response.setMessage("修改成功");
        return response;
    }

    /**
     * 通过手机号修改支付密码
     *
     * @param request
     * @param userModifyPayPasswordDTO
     * @return
     */
    @RequestMapping(value = "/payPassword/modifyByPhone", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO modifyPayPasswordByPhone(HttpServletRequest request,
                                                 @RequestBody WapUserModifyPayPasswordDTO userModifyPayPasswordDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        if (userModifyPayPasswordDTO == null || !StringUtil.isNotNull(userModifyPayPasswordDTO.getValidCode()) ||
                !StringUtil.isNotNull(userModifyPayPasswordDTO.getNewPassword()) || !StringUtil.isNotNull(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(2);
            response.setMessage("参数不能为空");
            return response;
        }
        //两次密码对比
        if (!userModifyPayPasswordDTO.getNewPassword().equals(userModifyPayPasswordDTO.getConfirmPassword())) {
            response.setCode(3);
            response.setMessage("密码输入不一致！");
            return response;
        }
        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            response.setCode(3);
            response.setMessage("用户信息查询失败，请稍后重试");
            return response;
        }
        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), userModifyPayPasswordDTO.getValidCode());
        if (validatePhone.getCode() != 1) {
            response.setCode(validatePhone.getCode());
            response.setMessage(validatePhone.getMessage());
            return response;
        }
        //判断支付密码是否与登录密码相同
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (userLog != null) {
            response.setCode(3);
            response.setMessage("不可与登录密码相同！");
            return response;
        }

        //修改支付密码
        boolean forgetPwd = userService.forgetPayPwd(user.getUserId(), MD5Util.toMd5(userModifyPayPasswordDTO.getNewPassword()));
        if (!forgetPwd) {
            response.setCode(3);
            response.setMessage("修改失败，请重试");
            return response;
        }
        response.setCode(1);
        response.setMessage("修改成功");
        return response;
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
            request.setAttribute("message", "登录过期");
            return "page/wap/login";
        }
        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户信息查询失败");
            return "/page/wap/login";
        }
        request.setAttribute("phoneAreaCode", userInfo.getPhoneAreaCode());
        request.setAttribute("phoneNumber", userInfo.getPhoneNumber());
        return "/page/wap/modifyPassword";
    }

    /**
     * 修改登录密码
     *
     * @param request
     * @param userModifyPasswordDTO
     * @return
     */
    @RequestMapping(value = "/password/modify", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO modifyPassword(HttpServletRequest request,
                                       @RequestBody WapUserModifyPayPasswordDTO userModifyPasswordDTO) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(4);
            response.setMessage("登录过期");
            return response;
        }
        if (userModifyPasswordDTO == null || !StringUtil.isNotNull(userModifyPasswordDTO.getValidCode()) || !StringUtil.isNotNull(userModifyPasswordDTO.getOldPassword()) ||
                !StringUtil.isNotNull(userModifyPasswordDTO.getNewPassword()) || !StringUtil.isNotNull(userModifyPasswordDTO.getConfirmPassword())) {
            response.setCode(2);
            response.setMessage("参数不能为空");
            return response;
        }
        //两次密码对比
        if (!userModifyPasswordDTO.getNewPassword().equals(userModifyPasswordDTO.getConfirmPassword())) {
            response.setCode(3);
            response.setMessage("密码输入不一致！");
            return response;
        }
        //新密码与原密码对比
        if (userModifyPasswordDTO.getNewPassword().equals(userModifyPasswordDTO.getOldPassword())) {
            response.setCode(3);
            response.setMessage("新密码不可与原密码相同");
            return response;
        }
        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            response.setCode(3);
            response.setMessage("用户信息查询失败，请稍后重试");
            return response;
        }
        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), userModifyPasswordDTO.getValidCode());
        if (validatePhone.getCode() != 1) {
            response.setCode(validatePhone.getCode());
            response.setMessage(validatePhone.getMessage());
            return response;
        }

        //原密码判定
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), MD5Util.toMd5(userModifyPasswordDTO.getOldPassword()));
        if (userLog == null) {
            response.setCode(3);
            response.setMessage("原密码错误！");
            return response;
        }
        //判断新密码是否与支付密码相同
        boolean userPay = userService.validateUserPay(user.getUserAccount(), MD5Util.toMd5(userModifyPasswordDTO.getNewPassword()));
        if (userPay) {
            response.setCode(3);
            response.setMessage("不可与支付密码相同！");
            return response;
        }
        //更新密码
        boolean forgetPwd = userService.forgetPwd(user.getUserAccount(), MD5Util.toMd5(userModifyPasswordDTO.getNewPassword()));
        if (!forgetPwd) {
            response.setCode(3);
            response.setMessage("修改失败，请重试");
            return response;
        }
        response.setCode(1);
        response.setMessage("修改成功");
        return response;
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
            request.setAttribute("message", "登录过期");
            return "page/wap/login";
        }
        UserDO userInfo = userService.getUserByUserId(user.getUserId());
        if (userInfo == null) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "用户信息查询失败");
            return "/page/wap/login";
        }
        request.setAttribute("phoneAreaCode", userInfo.getPhoneAreaCode());
        request.setAttribute("phoneNumber", userInfo.getPhoneNumber());
        return "page/wap/modifyPhone";
    }

    /**
     * 修改手机号
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/phone/modify", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBO modifyPhone(HttpServletRequest request,
                                    @RequestBody WapUserModifyPhoneDTO userModifyPhoneDTO) {
        JsonObjectBO response = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            response.setCode(2);
            response.setMessage("未登录");
            return response;
        }
        if (userModifyPhoneDTO == null) {
            response.setCode(3);
            response.setMessage("未接受到参数");
            return response;
        }
        String validateCode = StringUtil.stringNullHandle(userModifyPhoneDTO.getOldValidCode());
        String newVerifyCode = StringUtil.stringNullHandle(userModifyPhoneDTO.getNewValidCode());
        String password = StringUtil.stringNullHandle(userModifyPhoneDTO.getPassword());
        String areaCode = StringUtil.stringNullHandle(userModifyPhoneDTO.getAreaCode());
        String phone = StringUtil.stringNullHandle(userModifyPhoneDTO.getPhone());

        if (!StringUtil.isNotNull(validateCode) || !StringUtil.isNotNull(password) || !StringUtil.isNotNull(newVerifyCode)
                || !StringUtil.isNotNull(phone) || !StringUtil.isNotNull(areaCode)) {
            response.setCode(3);
            response.setMessage("未接受到参数");
            return response;
        }

        //获取用户信息
        UserDO userMessage = userService.getUserByUserId(user.getUserId());
        if (userMessage == null) {
            response.setCode(3);
            response.setMessage("用户信息查询失败，请稍后重试");
            return response;
        }

        if ((areaCode + phone).equals(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber())) {
            response.setCode(3);
            response.setMessage("新手机号不可与原手机号相同");
            return response;
        }
        //验证码判定
        JsonObjectBO validatePhone = systemValidatePhoneService.validatePhone(userMessage.getPhoneAreaCode() + userMessage.getPhoneNumber(), validateCode);
        if (validatePhone.getCode() != 1) {
            response.setCode(validatePhone.getCode());
            response.setMessage("原手机" + validatePhone.getMessage());
            return response;
        }

        //验证码判定
        validatePhone = systemValidatePhoneService.validatePhone(areaCode + phone, newVerifyCode);
        if (validatePhone.getCode() != 1) {
            response.setCode(validatePhone.getCode());
            response.setMessage("新手机" + validatePhone.getMessage());
            return response;
        }

        //登录密码判定
        password = MD5Util.toMd5(password);
        UserDO userLog = userService.validateUserLogin(user.getUserAccount(), password);
        if (userLog == null) {
            response.setCode(3);
            response.setMessage("登录密码错误！");
            return response;
        }

        //判定当前手机是否存在重复绑定
        UserDO userPhone = userService.getUserByPhone(phone);
        if (userPhone != null) {
            response.setCode(3);
            response.setMessage("该手机已被绑定");
            return response;
        }

        //手机号修改
        boolean updatePhone = userService.updatePhone(user.getUserId(), areaCode, phone);
        if (!updatePhone) {
            response.setCode(5);
            response.setMessage("修改失败");
            return response;
        }
        response.setCode(1);
        response.setMessage("修改成功");
        return response;
    }

    /**
     * 跳转到我的记录页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/myRecord/show.htm",method = RequestMethod.GET)
    public String toMyRecord(HttpServletRequest request) {
        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/wap/login";
        }
        return "page/wap/myRecord";
    }

}
