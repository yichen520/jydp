package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.VO.UserRechargeCoinRecordVO;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.ISylToJydpChainService;
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
 * web2.0用户充币记录
 * @author cyfIverson
 * @create 2018-04-20
 */
@RestController
@RequestMapping("/web/webUserRechargeCoinRecord")
@Scope(value = "prototype")
public class WebUserRechargeCoinRecordController {

    /**
     * SYL转账盛源链记录(SYL-->JYDP)
     */
    @Autowired
    private ISylToJydpChainService sylToJydpChainService;

    /**
     * 查询用户充币记录
     */
    @RequestMapping(value = "/getUsrRechargeCoinRecord.htm",method = RequestMethod.POST)
    public JsonObjectBO getUsrRechargeCoinRecord(HttpServletRequest request, @RequestBody String requestJson) {
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

        int totalNumber = sylToJydpChainService.countUserRechargeCoinRecordForUser(userSession.getUserId());

        int pageSize = 20;
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<UserRechargeCoinRecordVO> userRechargeCoinRecordList = null;
        if (totalNumber > 0) {
            userRechargeCoinRecordList = sylToJydpChainService.listUserRechargeCoinRecordForUser(userSession.getUserId(), pageNumber-1, pageSize);
        }

        jsonObject.put("pageNumber", pageNumber);
        jsonObject.put("totalNumber", totalNumber);
        jsonObject.put("totalPageNumber", totalPageNumber);
        jsonObject.put("userRechargeCoinRecordList", userRechargeCoinRecordList);

        responseJson.setData(jsonObject);
        responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);

        return responseJson;
    }
}
