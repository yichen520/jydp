package com.jydp.entity.DO.system;

import java.sql.Timestamp;

/**
 * 帮助中心
 * @author zym
 *
 */
public class SystemHelpDO {

    private int id; //记录Id
    private  String helpType; //帮助类型
    private String helpTitle; //帮助标题
    private String content; //帮助内容
    private Timestamp addTime; //添加时间

    /**
     * 记录Id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *  记录Id
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 帮助类型
     * @return helpType
     */
    public String getHelpType() {
        return helpType;
    }

    /**
     * 帮助类型
     * @param helpType the helpType to set
     */
    public void setHelpType(String helpType) {
        this.helpType = helpType;
    }

    /**
     * 帮助标题
     * @return helpTitle
     */
    public String getHelpTitle() {
        return helpTitle;
    }

    /**
     * 帮助标题
     * @param helpTitle the helpTitle to set
     */
    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    /**
     * 帮助内容
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 帮助内容
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 添加时间
     * @return addTime
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
