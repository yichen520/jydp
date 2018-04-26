package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.VO.TransactionUserDealVO;
import com.jydp.interceptor.WebInterceptor;
import com.jydp.service.ITransactionUserDealService;
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
 * web2.0 成交记录
 * @author cyfIverson
 * @create 2018-04-19
 */
@RestController
@RequestMapping("/web/webDealRecord")
@Scope(value = "prototype")
public class WebDealRecordController {

    /** 查询用户成交记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    @RequestMapping(value = "/getAccountRecord.htm",method = RequestMethod.POST)
    public JsonObjectBO getAccountRecord(HttpServletRequest request,@RequestBody String requestJson){
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO userSession = WebInterceptor.getUser(request);
        if (userSession == null) {
            responseJson.setCode(SystemMessageConfig.NOT_LOGININ_CODE);
            responseJson.setMessage(SystemMessageConfig.NOT_LOGININ_MESSAGE);
            return responseJson;
        }

        //参数获取
        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String pageNumberStr = String.valueOf(requestJsonObject.get("pageNumber"));
        String pendingOrderNo = StringUtil.stringNullHandle(requestJsonObject.get("pendingOrderNo")+"");

        int pageNumber = 1;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }
        int pageSize = 20;

        //获取一笔委托记录的成交记录
        if (StringUtil.isNotNull(pendingOrderNo)) {
            int totalNumber = transactionUserDealService.countTransactionUserDealByPendNo(pendingOrderNo, userSession.getUserId());

            int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
            if (totalPageNumber <= 0) {
                totalPageNumber = 1;
            }

            List<TransactionUserDealVO> dealRecordList = null;
            if (totalNumber > 0) {
                dealRecordList = transactionUserDealService.listTransactionUserDealByPendNo(pendingOrderNo, userSession.getUserId(), pageNumber-1, pageSize);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pageNumber",pageNumber);
            jsonObject.put("totalNumber",totalNumber);
            jsonObject.put("totalPageNumber",totalPageNumber);
            jsonObject.put("pendingOrderNo",pendingOrderNo);
            jsonObject.put("dealRecordList",dealRecordList);

            //成功
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
            return responseJson;
        }

        //获取该用户的成交记录
        int userId = userSession.getUserId();
        int totalNumber = transactionUserDealService.countUserDealForWeb(userId);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        List<TransactionUserDealVO> transactionUserDealList = null;
        if (totalNumber > 0) {
            transactionUserDealList = transactionUserDealService.getTransactionUserDeallist(userId,pageNumber-1,pageSize);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNumber",pageNumber);
        jsonObject.put("totalNumber",totalNumber);
        jsonObject.put("totalPageNumber",totalPageNumber);
        jsonObject.put("transactionUserDealList",transactionUserDealList);

        responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        responseJson.setData(jsonObject);
        return responseJson;
    }
}
