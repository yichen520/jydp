package com.jydp.entity.DO.transaction;

import java.sql.Timestamp;

/**
 * 交易币种
 *
 * @author fk
 */
public class TransactionCurrencyDO {
    private int currencyId;  //币种Id
    private String currencyShortName;  //货币简称
    private String currencyName;  //货币名称
    private String currencyImg;  //币种徽标
    private double buyFee;  //买入手续费
    private double sellFee;  //卖出手续费
    private double guidancePrice;  //上市指导价
    private int paymentType;  //交易状态,1:正常，2:停牌
    private int upStatus;  //上线状态,1:待上线,2:上线中,3:停牌,4:已下线
    private int rankNumber;  //排名位置
    private String backerAccount;  //管理员账号
    private String ipAddress;  //操作时的ip地址
    private Timestamp upTime;  //上线时间
    private Timestamp addTime;  //添加时间

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
     * 货币简称
     *
     * @return the currencyShortName
     */
    public String getCurrencyShortName() {
        return currencyShortName;
    }

    /**
     * 货币简称
     *
     * @param currencyShortName the currencyShortName to set
     */
    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
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
     * 币种徽标
     * @return the currencyImg
     */
    public String getCurrencyImg() {
        return currencyImg;
    }

    /**
     * 币种徽标
     * @param currencyImg the currencyImg to set
     */
    public void setCurrencyImg(String currencyImg) {
        this.currencyImg = currencyImg;
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

    /**
     * 排名位置
     * @param rankNumber the rankNumber to set
     */
    public void setRankNumber(int rankNumber) {
        this.rankNumber = rankNumber;
    }

    /**
     * 管理员账号
     * @return the backerAccount
     */
    public String getBackerAccount() {
        return backerAccount;
    }

    /**
     * 管理员账号
     * @param backerAccount the backerAccount to set
     */
    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }

    /**
     * 操作时的ip地址
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 操作时的ip地址
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 上线时间
     * @return the upTime
     */
    public Timestamp getUpTime() {
        return upTime;
    }

    /**
     * 上线时间
     * @param upTime the upTime to set
     */
    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
    }

    /**
     * 添加时间
     *
     * @return the addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     *
     * @param addTime the add time to set
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    /**
     *  上市指导价
     * @return the guidancePrice
     */
    public double getGuidancePrice() {
        return guidancePrice;
    }

    /**
     *  上市指导价
     * @param guidancePrice the guidancePrice to set
     */
    public void setGuidancePrice(double guidancePrice) {
        this.guidancePrice = guidancePrice;
    }
}
