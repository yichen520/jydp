package com.jydp.entity.DO.syl;

import java.sql.Timestamp;

/**
 * 盛源链账号绑定
 *
 * @author sy
 */
public class SylUserBoundDO {
    private int id;  //记录id
    private int userId;  //用户id
    private String userAccount;  //用户帐号
    private String userSylAccount;  //盛源链APP用户帐号
    private int status;  //绑定状态 1,绑定2，解绑（预留字段）
    private Timestamp addTime;  //添加时间

    /**
     * 记录id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * 记录id
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 用户id
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户id
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 用户帐号
     *
     * @return the user account
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 用户帐号
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
     * 绑定状态 1,绑定2，解绑（预留字段）
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * 绑定状态 1,绑定2，解绑（预留字段）
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 添加时间
     *
     * @return the addTime
     */
    public Timestamp getAddtime() {
        return addTime;
    }

    /**
     * 添加时间
     *
     * @param addTime the addTime
     */
    public void setAddtime(Timestamp addTime) {
        this.addTime = addTime;
    }
}
