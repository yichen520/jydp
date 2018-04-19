package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.Base64Util;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.IOtcDealerUserService;
import com.jydp.service.IUserIdentificationImageService;
import com.jydp.service.IUserIdentificationService;
import com.jydp.service.IUserService;
import config.SystemMessageConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yqz
 * 用户登录登出
 */
@RestController
@RequestMapping("/web/userLogin")
public class WebUserLoginController {


    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageService userIdentificationImageService;

    /** 用户标识经销商相关操作*/
    @Autowired
    private IOtcDealerUserService otcDealerUserService;

    @RequestMapping(value = "/login1",method = RequestMethod.GET)
    public String getString(){
        return "123";
    }
    /**
     * 用户登录验证
     * @return wap首页
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JsonObjectBO userLogin(@Param("userAccount") String userAccount, @Param("password") String password, HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();
        if(!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password)){
            responseJson.setCode(SystemMessageConfig.USER_ACCOUNT_OR_PASSWORD_ISNULL_CODE);
            responseJson.setMessage(SystemMessageConfig.USER_ACCOUNT_OR_PASSWORD_ISNULL_MESSAGE);
            return responseJson;
        }

        password = MD5Util.toMd5(password);
        UserDO user = userService.validateUserLogin(userAccount, password);
        if (user == null) {
            responseJson.setCode(SystemMessageConfig.USER_ACCOUNT_OR_PASSWORD_ERROR_CODE);
            responseJson.setMessage(SystemMessageConfig.USER_ACCOUNT_OR_PASSWORD_ERROR_MESSAGE);
            return responseJson;
        }
/*

        //查询用户最新认证信息
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationByUserAccountLately(user.getUserAccount());

        //未进行认证
        if (userIdentification == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",user.getUserId());
            jsonObject.put("userAccount",user.getUserAccount());

            responseJson.setData(jsonObject);
            responseJson.setCode(4);
            responseJson.setMessage("未进行认证");
            return responseJson;
        }
        //认证未通过
        if (userIdentification.getIdentificationStatus() != 2) {
            List<UserIdentificationImageDO> userIdentificationImageList =
                    userIdentificationImageService.listUserIdentificationImageByIdentificationId(userIdentification.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",user.getUserId());
            jsonObject.put("userAccount",user.getUserAccount());
            jsonObject.put("identification",userIdentification);
            jsonObject.put("identificationImageList",userIdentificationImageList);

            responseJson.setData(jsonObject);
            responseJson.setCode(5);
            responseJson.setMessage("未通过认证");
            return responseJson;
        }

        // 用户被禁用
        if (user.getAccountStatus() != 1) {
            responseJson.setCode(6);
            responseJson.setMessage("用户被禁用");
            return responseJson;
        }
*/

        UserSessionBO userSessionBO = new UserSessionBO();
        userSessionBO.setUserId(user.getUserId());
        userSessionBO.setUserAccount(user.getUserAccount());
        userSessionBO.setIsPwd(1);
        OtcDealerUserDO otcDealerUserDO = otcDealerUserService.getOtcDealerUserByUserId(user.getUserId());
        if(otcDealerUserDO != null){
            userSessionBO.setIsDealer(2);//是经销商
        }else{
            userSessionBO.setIsDealer(1);//不是经销商
        }
        WebInterceptor.loginSuccess(request, userSessionBO);

        responseJson.setCode(SystemMessageConfig.LOGIN_SUCCESS_CODE);
        responseJson.setMessage(SystemMessageConfig.LOGIN_SUCCESS_MESSAGE);
        return responseJson;
    }

    /** 退出登录 */
    @RequestMapping(value = "/loginOut")
    public JsonObjectBO loginOut(HttpServletRequest request) {
        UserWapInterceptor.loginOut(request);
        JsonObjectBO responseJson = new JsonObjectBO();

        responseJson.setCode(SystemMessageConfig.LOGOUT_SUCCESS_CODE);
        responseJson.setMessage(SystemMessageConfig.LOGOUT_SUCCESS_MESSAGE);
        return responseJson;
    }
}
