package com.jydp.controller.syl;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.SignatureUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserService;
import config.SylConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.TreeMap;

/**
 * SYL转账盛源链(JYDP-->SYL)
 * @author sy
 */
@Controller
@Scope(value = "prototype")
public class JydpGoSylDealController {

    /** JYDP用户币种转出记录	*/
    @Autowired
    IJydpUserCoinOutRecordService jydpUserCoinOutRecordService;

    /** 交易币种 */
    @Autowired
    ITransactionCurrencyService transactionCurrencyService;

    /** 用户管理 */
    @Autowired
    private IUserService userService;

    /** 接收SYL转账盛源链成功回调请求 */
    @RequestMapping(value = "/getCoinNotify", method = RequestMethod.POST)
    public @ResponseBody JSONObject getCoinNotify(@RequestBody String requestJsonString) {
        JSONObject responseJson = new JSONObject();
        JSONObject requestJson = null;
        try {
            requestJson = JSONObject.parseObject(requestJsonString);
        } catch (Exception e) {
            responseJson.put("code", 3);
            responseJson.put("message", "JSON格式错误");
            return responseJson;
        }

        String orderNo = StringUtil.stringNullHandle(requestJson.getString("orderNo"));
        String recordNo = StringUtil.stringNullHandle(requestJson.getString("recordNo"));
        String code = StringUtil.stringNullHandle(requestJson.getString("code"));
        String coinType = StringUtil.stringNullHandle(requestJson.getString("coinType"));
        String receiveTimeStr = StringUtil.stringNullHandle(requestJson.getString("receiveTime"));
        String key = StringUtil.stringNullHandle(requestJson.getString("key"));

        if (!StringUtil.isNotNull(orderNo) || !StringUtil.isNotNull(recordNo) || !StringUtil.isNotNull(code) || !StringUtil.isNotNull(coinType)
                || !StringUtil.isNotNull(receiveTimeStr) || !StringUtil.isNotNull(key)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }

        //签名验证
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("orderNo", orderNo);
        map.put("recordNo", recordNo);
        map.put("code", code);
        map.put("coinType", coinType);
        map.put("receiveTime", receiveTimeStr);

        String signature = SignatureUtil.getSign(map, SylConfig.SIGN_SECRET_KEY);
        if(!key.equals(signature)){
            responseJson.put("code", 3);
            responseJson.put("message", "签名错误");
            return responseJson;
        }

        //根据订单号查询相关订单信息
        JydpUserCoinOutRecordDO jydpUserCoinOutRecord = jydpUserCoinOutRecordService.getJydpUserCoinOutRecordByRecordNo(orderNo);
        if(jydpUserCoinOutRecord == null){
            responseJson.put("code", 402);
            responseJson.put("message", "无效订单号");
            return responseJson;
        }

        //查询币种信息
        TransactionCurrencyVO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyShortName(coinType);
        if(transactionCurrency == null){
            responseJson.put("code", 403);
            responseJson.put("message", "无效币种参数");
            return responseJson;
        }

        //根据币种及电子钱包操作记录号判定是否存在该订单回调
        JydpUserCoinOutRecordDO jydpUserCoinOut = jydpUserCoinOutRecordService.getJydpUserCoinOutRecordByRecordNoAndCoinType(recordNo, transactionCurrency.getCurrencyId());
        if(jydpUserCoinOut != null){
            if(jydpUserCoinOut.getOutStatus() == 2){
                responseJson.put("code", 1);
                responseJson.put("message", "该订单已被修改");
                return responseJson;
            }
        }

        //判定订单状态
        Timestamp receiveTime = Timestamp.valueOf(receiveTimeStr);
        boolean updateSyl;
        if(code.equals("1")){
            updateSyl = jydpUserCoinOutRecordService.updateJydpUserCoinOutRecordBySyl(orderNo, recordNo,transactionCurrency.getCurrencyId(), 3, receiveTime);
        } else {
            updateSyl = jydpUserCoinOutRecordService.updateJydpUserCoinOutRecordBySyl(orderNo, recordNo,transactionCurrency.getCurrencyId(), 4, receiveTime);
        }

        if(!updateSyl){
            responseJson.put("code", 404);
            responseJson.put("message", "订单信息修改失败");
            return responseJson;
        }
        responseJson.put("code", 1);
        responseJson.put("message", "回调接收成功");
        return responseJson;
    }


}
