package com.jydp.entity.DTO;

/**
 * k线图币种信息
 * @author whx
 */
public class KGraphCurrencyDTO {

    private int currencyId;  //币种Id
    private int upStatus;  //上线状态,1:待上线,2:上线中,3:停牌,4:已下线

    /**
     *币种Id
     * @return 币种Id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     *币种Id
     * @param currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @return 上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     */
    public int getUpStatus() {
        return upStatus;
    }

    /**
     * 上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param upStatus
     */
    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
    }
}
