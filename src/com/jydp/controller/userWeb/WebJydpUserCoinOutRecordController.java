package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.IUserService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * web2.0用户币种提现记录
 * @author cyfIVerson
 * @create 2018-04-20
 */
@RestController
@RequestMapping("/web/webjydpUserCoinOutRecord")
@Scope(value="prototype")
public class WebJydpUserCoinOutRecordController {

    /** JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordService jydpUserCoinOutRecordService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;
    
    /** 查询用户币种转出记录 */
    @RequestMapping(value = "/getUserCoinOutRecord.htm",method = RequestMethod.POST)
    public JsonObjectBO getJydpUserCoinOutRecord(HttpServletRequest request, @RequestBody String requestJson){
        JsonObjectBO responseJson = new JsonObjectBO();
        JSONObject jsonObject = new JSONObject();

        UserSessionBO userSession = WebInterceptor.getUser(request);
        //未登录
        if (userSession == null) {
            responseJson.setCode(SystemMessageConfig.NOT_LOGININ_CODE);
            responseJson.setMessage(SystemMessageConfig.NOT_LOGININ_MESSAGE);
            return responseJson;
        }

        //参数获取
        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String pageNumberStr = String.valueOf(requestJsonObject.get("pageNumber"));

        int pageNumber = 1;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int userId = userSession.getUserId();
        int totalNumber = jydpUserCoinOutRecordService.countJydpUserCoinOutRecord(userId);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        if (totalNumber > 0) {
            jydpUserCoinOutRecordList = jydpUserCoinOutRecordService.getJydpUserCoinOutRecordlist(userId,pageNumber-1,pageSize);
        }

        jsonObject.put("pageNumber",pageNumber);
        jsonObject.put("totalNumber", totalNumber);
        jsonObject.put("totalPageNumber", totalPageNumber);
        jsonObject.put("coinOutRecordList", jydpUserCoinOutRecordList);
        responseJson.setData(jsonObject);
        responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        return responseJson;
    }

    /** 撤销用户币种转出申请 */
    @RequestMapping(value = "/withdrawCoinOutRecord.htm",method = RequestMethod.POST)
    public JsonObjectBO withdrawUserCoinOutRecord(HttpServletRequest request,@RequestBody String requestJson){
        JsonObjectBO responseJson = new JsonObjectBO();

        //获取参数
        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String coinRecordNo = (String) requestJsonObject.get("coinRecordNo");

        if (StringUtil.isNull(coinRecordNo)) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        UserSessionBO userSession = UserWebInterceptor.getUser(request);

        //未登录
        if (userSession == null) {
            responseJson.setCode(SystemMessageConfig.NOT_LOGININ_CODE);
            responseJson.setMessage(SystemMessageConfig.NOT_LOGININ_MESSAGE);
            return responseJson;
        }

        int userId = userSession.getUserId();
        //获取用户信息
        UserDO user = userService.getUserByUserId(userId);
        if(user == null){
            responseJson.setCode(SystemMessageConfig.USER_ISEXIST_CODE);
            responseJson.setMessage(SystemMessageConfig.USER_ISEXIST_MESSAGE);
            return responseJson;
        }

        if(user.getAccountStatus() != 1){
            responseJson.setCode(SystemMessageConfig.ACCOUNT_DISABLED_CODE);
            responseJson.setMessage(SystemMessageConfig.ACCOUNT_DISABLED_MESSAGE);
            return responseJson;
        }

        boolean result = jydpUserCoinOutRecordService.withdrawUserCoinOutRecord(userId,coinRecordNo);

        if (result) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        } else {
            responseJson.setCode(SystemMessageConfig.UNDO_FAILED_CODE);
            responseJson.setMessage(SystemMessageConfig.UNDO_FAILED_MESSAGE);
        }
        return responseJson;
    }

}
