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
    private String userSylAccount;  //盛源链APP用户帐号
    private String chainAddress;  //盛源链地址
    private String chainAddressName;  //盛源链地址别名
    private double shengyuanCoin;  //盛源币
    private double tranRatio;  //交易费率
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
     * 盛源链APP用户帐号
     *
     * @return the user syl account
     */
    public String getUserSylAccount() {
        return userSylAccount;
    }

    /**
     * 盛源链APP用户帐号
     *
     * @param userSylAccount the user syl account
     */
    public void setUserSylAccount(String userSylAccount) {
        this.userSylAccount = userSylAccount;
    }

    /**
     * 盛源链地址
     *
     * @return the chain address
     */
    public String getChainAddress() {
        return chainAddress;
    }

    /**
     * 盛源链地址
     *
     * @param chainAddress the chain address
     */
    public void setChainAddress(String chainAddress) {
        this.chainAddress = chainAddress;
    }

    /**
     * 盛源链地址别名
     *
     * @return the chain address name
     */
    public String getChainAddressName() {
        return chainAddressName;
    }

    /**
     * 盛源链地址别名
     *
     * @param chainAddressName the chain address name
     */
    public void setChainAddressName(String chainAddressName) {
        this.chainAddressName = chainAddressName;
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
     * 交易费率
     *
     * @return the tran ratio
     */
    public double getTranRatio() {
        return tranRatio;
    }

    /**
     * 交易费率
     *
     * @param tranRatio the tran ratio
     */
    public void setTranRatio(double tranRatio) {
        this.tranRatio = tranRatio;
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
