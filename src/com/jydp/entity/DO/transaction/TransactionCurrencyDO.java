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
    private double upRange;  //涨停幅度
    private double downRange;  //跌停幅度
    private int paymentType;  //交易状态,1:正常，2:涨停，3:跌停，4:停牌
    private int upStatus;  //上线状态,1:待上线,2:上线中,3:禁用,4:已下线
    private String backerAccount;  //管理员账号
    private String ipAddress;  //操作时的ip地址
    private Timestamp startTime;  //交易开始时间
    private Timestamp endTime;  //交易结束时间
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
     * 涨停幅度
     * @return the upRange
     */
    public double getUpRange() {
        return upRange;
    }

    /**
     * 涨停幅度
     * @param upRange the upRange to set
     */
    public void setUpRange(double upRange) {
        this.upRange = upRange;
    }

    /**
     * 跌停幅度
     * @return the downRange
     */
    public double getDownRange() {
        return downRange;
    }

    /**
     * 跌停幅度
     * @param downRange the downRange to set
     */
    public void setDownRange(double downRange) {
        this.downRange = downRange;
    }

    /**
     * 交易状态, 1:正常，2:涨停，3:跌停
     * @return the paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * 交易状态, 1:正常，2:涨停，3:跌停
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 上线状态,1:待上线,2:上线中,3:禁用
     * @return the upStatus
     */
    public int getUpStatus() {
        return upStatus;
    }

    /**
     * 上线状态,1:待上线,2:上线中,3:禁用
     * @param upStatus the upStatus to set
     */
    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
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
     * 交易开始时间
     * @return the startTime
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * 交易开始时间
     * @param startTime the startTime to set
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * 交易结束时间
     * @return the endTime
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * 交易结束时间
     * @param endTime the endTime to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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
     * @param addTime the add time
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
