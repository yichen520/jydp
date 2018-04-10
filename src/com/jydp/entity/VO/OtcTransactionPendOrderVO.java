package com.jydp.entity.VO;

import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;

/**
 * 场外交易挂单记录
 * @author yk
 */
public class OtcTransactionPendOrderVO extends OtcTransactionPendOrderDO{
    private String dealerName; //经销商名称

    /**
     * 获取 经销商名称
     * @return dealerName
     */
    public String getDealerName() {
        return dealerName;
    }

    /**
     * 设置 经销商名称
     * @param dealerName 经销商名称
     */
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
