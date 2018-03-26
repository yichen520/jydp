package com.jydp.service.impl.syl;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.OKHttpUtil;
import com.iqmkj.utils.SignatureUtil;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.service.IJydpToSylService;
import com.jydp.service.IJydpUserCoinOutRecordService;
import config.SylConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

/**
 * JYDP转账盛源链(JYDP-->SYL)
 * @author hz
 */
@Service("jydpToSylService")
public class JydpToSylServiceImpl implements IJydpToSylService {

    /** JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordService jydpUserCoinOutRecordService;

    /**
     * 交易大盘向盛源链钱包转币申请
     */
    public void jydpTosylApply(){
        //查询审核通过但未推送的
        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = jydpUserCoinOutRecordService.listNotPushRecord();
        if(jydpUserCoinOutRecordList == null){
            return;
        }

        List<String> coinRecordNoList = null;
        for (JydpUserCoinOutRecordDO jydpUserCoinOutRecord: jydpUserCoinOutRecordList) {
            String orderNo = jydpUserCoinOutRecord.getCoinRecordNo();
            String userAccount = jydpUserCoinOutRecord.getUserAccount();
            double currencyNumber = jydpUserCoinOutRecord.getCurrencyNumber();
            String coin = Double.toString(currencyNumber);

            //进行参数加密
            TreeMap<String, String> map = new TreeMap<>();
            map.put("orderNo", orderNo);
            map.put("coinType", SylConfig.SHENYUAN_COIN);
            map.put("userAccount", userAccount);
            map.put("coin", coin);
            String md5KeyStr = SignatureUtil.getSign(map, SylConfig.SIGN_SECRET_KEY);

            //封装参数
            JSONObject requestJson = new JSONObject();
            requestJson.put("orderNo", orderNo);
            requestJson.put("userAccount", userAccount);
            requestJson.put("coin", coin);
            requestJson.put("coinType", SylConfig.SHENYUAN_COIN);
            requestJson.put("key", md5KeyStr);

            //发送请求
            JSONObject responseJson = OKHttpUtil.postRequestJson(SylConfig.WITHDRAWALS_COIN_APPLY_URL, requestJson);

            //打印日志
            if (responseJson != null) {
                LogUtil.printInfoLog(responseJson.toJSONString());
            }

            //处理返回值
            if (responseJson != null
                    && responseJson.containsKey("code")
                    && responseJson.getInteger("code") == 1) {
                coinRecordNoList.add(orderNo);
            }
        }
        //批量修改状态
        jydpUserCoinOutRecordService.updateJydpUserCoinOutRecordOutStatus(coinRecordNoList);
    }
}
