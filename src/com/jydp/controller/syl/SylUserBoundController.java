package com.jydp.controller.syl;


import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.SignatureUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.syl.SylUserBoundDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.service.ISylUserBoundService;
import com.jydp.service.IUserIdentificationService;
import com.jydp.service.IUserService;
import config.SylConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.TreeMap;

/**
 * 盛源链账号绑定
 *
 * @author sy
 */
@Controller
@Scope(value = "prototype")
public class SylUserBoundController {

    /** 盛源链账号绑定 */
    @Autowired
    private ISylUserBoundService sylUserBoundService;

    /** 用户管理 */
    @Autowired
    private IUserService userService;

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 盛源链账号绑定请求接收 */
    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    public @ResponseBody JSONObject bindUser(@RequestBody String requestJsonString) {
        JSONObject responseJson = new JSONObject();
        JSONObject requestJson = null;
        try {
            requestJson = JSONObject.parseObject(requestJsonString);
        } catch (Exception e) {
            responseJson.put("code", 3);
            responseJson.put("message", "JSON格式错误");
            return responseJson;
        }

        String sylUserAccount = StringUtil.stringNullHandle(requestJson.getString("sylUserAccount"));
        String userAccount = StringUtil.stringNullHandle(requestJson.getString("userAccount"));
        String password = StringUtil.stringNullHandle(requestJson.getString("password"));
        String uname = StringUtil.stringNullHandle(requestJson.getString("uname"));
        String idCard = StringUtil.stringNullHandle(requestJson.getString("idCard"));
        String key = StringUtil.stringNullHandle(requestJson.getString("key"));

        if (!StringUtil.isNotNull(sylUserAccount) || !StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(password)|| !StringUtil.isNotNull(uname)
                || !StringUtil.isNotNull(idCard) || !StringUtil.isNotNull(key)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }

        //签名验证
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("sylUserAccount", sylUserAccount);
        map.put("userAccount", userAccount);
        map.put("password", password);
        map.put("uname", uname);
        map.put("idCard", idCard);

        String signature = SignatureUtil.getSign(map, SylConfig.SIGN_SECRET_KEY);
        if(!key.equals(signature)){
            responseJson.put("code", 3);
            responseJson.put("message", "签名错误");
            return responseJson;
        }

        //判定是否存在该账号
        UserDO user = userService.getUserByUserAccount(userAccount);
        if(user == null){
            responseJson.put("code", 101);
            responseJson.put("message", "请前往Exchange Technology（EXT）进行注册");
            return responseJson;
        }

        //密码判定
        user = userService.validateUserLogin(userAccount, password);
        if(user == null){
            responseJson.put("code", 102);
            responseJson.put("message", "账号或密码错误");
            return responseJson;
        }

        //查询用户最新认证信息
        UserIdentificationDO userIdentification = userIdentificationService.getUserIdentificationByUserAccountLately(userAccount);

        //未进行认证
        if (userIdentification == null) {
            responseJson.put("code", 103);
            responseJson.put("message", "该用户未进行实名认证");
            return responseJson;
        }
        //认证未通过
        if (userIdentification.getIdentificationStatus() != 2) {
            responseJson.put("code", 104);
            responseJson.put("message", "该用户未通过实名认证");
            return responseJson;
        }

        if (user.getAccountStatus() != 1) {
            responseJson.put("code", 105);
            responseJson.put("message", "该用户被禁用");
            return responseJson;
        }

        if(!userIdentification.getUserCertNo().equals(idCard) || !userIdentification.getUserName().equals(uname)){
            responseJson.put("code", 106);
            responseJson.put("message", "绑定账号实名认证信息不匹配,请选择本人身份认证信息账号进行绑定操作");
            return responseJson;
        }

        //验证交易大盘用户是否存在绑定信息
        SylUserBoundDO sylUserBound = sylUserBoundService.getSylUserBoundByUserId(user.getUserId());
        if(sylUserBound != null){
            responseJson.put("code", 107);
            responseJson.put("message", "该用户已存在绑定账号");
            return responseJson;
        }

        //验证盛源链用户是否存在绑定信息
        sylUserBound = sylUserBoundService.getSylUserBoundBySylUserAccount(sylUserAccount);
        if(sylUserBound != null){
            responseJson.put("code", 108);
            responseJson.put("message", "您已存在绑定账号");
            return responseJson;
        }

        sylUserBound = new SylUserBoundDO();
        sylUserBound.setUserId(user.getUserId());
        sylUserBound.setUserAccount(user.getUserAccount());
        sylUserBound.setUserSylAccount(sylUserAccount);
        sylUserBound.setAddtime(DateUtil.getCurrentTime());

        boolean insertSylUserBound = sylUserBoundService.insertSylUserBound(sylUserBound);
        if(!insertSylUserBound){
            responseJson.put("code", 109);
            responseJson.put("message", "绑定失败");
            return responseJson;
        }

        responseJson.put("code", 1);
        responseJson.put("message", "绑定成功");
        return responseJson;
    }
}
