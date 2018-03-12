package com.jydp.entity.DTO;

/**
 * 交易币种基本信息
 * @author whx
 */
public class TransactionCurrencyBasicDTO {

    private int currencyId;  //币种Id
    private String currencyName;  //货币名称
    private double buyFee;  //买入手续费
    private double sellFee;  //卖出手续费
    private int paymentType;  //交易状态,1:正常，2:停牌
    private int upStatus;  //上线状态,1:待上线,2:上线中,3:停牌,4:已下线
    private int rankNumber;  //排名位置

    /**
     * 币种Id
     *
     * @return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种Id
     *
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 货币名称
     *
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * 货币名称
     *
     * @param currencyName the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * 买入手续费
     * @return the buyFee
     */
    public double getBuyFee() {
        return buyFee;
    }

    /**
     * 买入手续费
     * @param buyFee the buyFee to set
     */
    public void setBuyFee(double buyFee) {
        this.buyFee = buyFee;
    }

    /**
     * 卖出手续费
     * @return the sellFee
     */
    public double getSellFee() {
        return sellFee;
    }

    /**
     * 卖出手续费
     * @param sellFee the sellFee to set
     */
    public void setSellFee(double sellFee) {
        this.sellFee = sellFee;
    }

    /**
     * 交易状态,1:正常，2:停牌
     * @return the paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * 交易状态,1:正常，2:停牌
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @return the upStatus
     */
    public int getUpStatus() {
        return upStatus;
    }

    /**
     * 上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param upStatus the upStatus to set
     */
    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
    }

    /**
     * 排名位置
     * @return the rankNumber
     */
    public int getRankNumber() {
        return rankNumber;
    }

}
