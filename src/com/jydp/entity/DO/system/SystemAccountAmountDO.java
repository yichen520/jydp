package com.jydp.entity.DO.system;

import java.sql.Timestamp;

/**
 * 系统账户金额
 * @author hz
 *
 */
public class SystemAccountAmountDO {

    private int accountCode;  //账号编码，详见《系统账户编码表》
    private String accountName;  //账户名称
    private double accountAmount;  //账户金额，单位：美元
    private Timestamp addTime;  //添加时间

    /**
     * 账号编码，详见《系统账户编码表》
     * @return the accountCode
     */
    public int getAccountCode() {
        return accountCode;
    }

    /**
     * 账号编码，详见《系统账户编码表》
     * @param accountCode the accountCode to set
     */
    public void setAccountCode(int accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * 账户名称
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 账户名称
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 账户金额，单位：美元
     * @return the accountAmount
     */
    public double getAccountAmount() {
        return accountAmount;
    }

    /**
     * 账户金额，单位：美元
     * @param accountAmount the accountAmount to set
     */
    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    /**
     * 添加时间
     * @return the addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     * @param addTime the addTime to set
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
