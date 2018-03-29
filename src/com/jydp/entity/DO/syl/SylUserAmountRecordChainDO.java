package com.jydp.entity.DO.syl;

import java.sql.Timestamp;

/**
 * SYL转账盛源链记录(JYDP-->SYL)
 *
 * @author sy
 */
public class SylUserAmountRecordChainDO {
    private String recordNo;  //记录号 : 业务类型（2）+日期（6）+随机位（7）
    private String sylRecordNo;  //盛源链记录号
    private String userAccount;  //用户账号
    private String coinType;  //币种类型
    private double shengyuanCoin;  //盛源币
    private int handleStatus;  //操作状态 1：操作中，2：操作成功，3：操作失败
    private String handleMark;  //操作说明
    private Timestamp handleTime;  //操作时间 : 盛源链app完成时间
    private Timestamp addTime;  //添加时间

    /**
     * 记录号 : 业务类型（2）+日期（6）+随机位（7）
     *
     * @return the record no
     */
    public String getRecordNo() {
        return recordNo;
    }

    /**
     * 记录号 : 业务类型（2）+日期（6）+随机位（7）
     *
     * @param recordNo the record no
     */
    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    /**
     * 盛源链记录号
     *
     * @return the syl record no
     */
    public String getSylRecordNo() {
        return sylRecordNo;
    }

    /**
     * 盛源链记录号
     *
     * @param sylRecordNo the syl record no
     */
    public void setSylRecordNo(String sylRecordNo) {
        this.sylRecordNo = sylRecordNo;
    }

    /**
     * 用户账号
     *
     * @return the user account
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户账号
     *
     * @param userAccount the user account
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 币种类型
     *
     * @return the coin type
     */
    public String getCoinType() {
        return coinType;
    }

    /**
     * 币种类型
     *
     * @param coinType the coin type
     */
    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    /**
     * 盛源币
     *
     * @return the shengyuan coin
     */
    public double getShengyuanCoin() {
        return shengyuanCoin;
    }

    /**
     * 盛源币
     *
     * @param shengyuanCoin the shengyuan coin
     */
    public void setShengyuanCoin(double shengyuanCoin) {
        this.shengyuanCoin = shengyuanCoin;
    }

    /**
     * 操作状态 1：操作中，2：操作成功，3：操作失败
     *
     * @return the handle status
     */
    public int getHandleStatus() {
        return handleStatus;
    }

    /**
     * 操作状态 1：操作中，2：操作成功，3：操作失败
     *
     * @param handleStatus the handle status
     */
    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * 操作说明
     *
     * @return the handle mark
     */
    public String getHandleMark() {
        return handleMark;
    }

    /**
     * 操作说明
     *
     * @param handleMark the handle mark
     */
    public void setHandleMark(String handleMark) {
        this.handleMark = handleMark;
    }

    /**
     * 操作时间 : 盛源链app完成时间
     *
     * @return the handle time
     */
    public Timestamp getHandleTime() {
        return handleTime;
    }

    /**
     * 操作时间 : 盛源链app完成时间
     *
     * @param handleTime the handle time
     */
    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
    }

    /**
     * 添加时间
     *
     * @return the add time
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
