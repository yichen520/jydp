package com.jydp.entity.DO.syl;


import java.sql.Timestamp;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
public class SylToJydpChainDO {
    private String sylRecordNo;  //盛源链记录号
    private int userId;  //用户id
    private String userAccount;  //用户账号
    private String coinType;  //币种类型
    private double shengyuanCoin;  //盛源币
    private String handleMark;  //操作说明
    private Timestamp addTime;  //添加时间


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
     * 用户id
     *
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户id
     *
     * @param userId the userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
