package com.jydp.entity.DO.user;


import java.sql.Timestamp;

/**
 * 意见反馈
 *
 * @author hht
 */
public class UserFeedbackDO {

    private long id;  //记录Id
    private int userId;  //用户Id
    private String userAccount;  //用户账号
    private String feedbackTitle;  //反馈标题
    private String feedbackContent;  //反馈内容
    private Timestamp addTime;  //反馈时间
    private int handleStatus;  //处理状态，1：待处理，2：处理中，3：已处理
    private String handleContent;  //处理说明
    private String backerAccount;  //处理的后台管理员帐号
    private Timestamp handleTime;  //处理时间


    /**
     * 记录Id
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * 记录Id
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * 用户Id
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 用户Id
     *
     * @param userId the user id
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
     * 反馈标题
     *
     * @return the feedback title
     */
    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    /**
     * 反馈标题
     *
     * @param feedbackTitle the feedback title
     */
    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }


    /**
     * 反馈内容
     *
     * @return the feedback content
     */
    public String getFeedbackContent() {
        return feedbackContent;
    }

    /**
     * 反馈内容
     *
     * @param feedbackContent the feedback content
     */
    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }


    /**
     * 反馈时间
     *
     * @return the add time
     */
    public java.sql.Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 反馈时间
     *
     * @param addTime the add time
     */
    public void setAddTime(java.sql.Timestamp addTime) {
        this.addTime = addTime;
    }


    /**
     * 处理状态，1：待处理，2：处理中，3：已处理
     *
     * @return the handle status
     */
    public int getHandleStatus() {
        return handleStatus;
    }

    /**
     * 处理状态，1：待处理，2：处理中，3：已处理
     *
     * @param handleStatus the handle status
     */
    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }


    /**
     * 处理说明
     *
     * @return the handle content
     */
    public String getHandleContent() {
        return handleContent;
    }

    /**
     * 处理说明
     *
     * @param handleContent the handle content
     */
    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }


    /**
     * 处理的后台管理员帐号
     *
     * @return the backer account
     */
    public String getBackerAccount() {
        return backerAccount;
    }

    /**
     * 处理的后台管理员帐号
     *
     * @param backerAccount the backer account
     */
    public void setBackerAccount(String backerAccount) {
        this.backerAccount = backerAccount;
    }


    /**
     * 处理时间
     *
     * @return the handle time
     */
    public java.sql.Timestamp getHandleTime() {
        return handleTime;
    }

    /**
     * 处理时间
     *
     * @param handleTime the handle time
     */
    public void setHandleTime(java.sql.Timestamp handleTime) {
        this.handleTime = handleTime;
    }

}
