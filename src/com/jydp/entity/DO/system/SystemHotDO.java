package com.jydp.entity.DO.system;

import config.FileUrlConfig;

import java.sql.Timestamp;

/**
 * 热门话题
 * @author zym
 *
 */
public class SystemHotDO {

    private int id; //记录Id
    private String noticeTitle; //话题标题
    private String noticeUrl; //话题封面图地址
    private String content; //话题内容
    private Timestamp addTime; //添加时间
    private Timestamp topTime; //置顶时间

    private String noticeUrlFormat; //话题封面图绝对路径

    /**
     * 话题封面图绝对路径
     * @return noticeUrlFormat
     */
    public String getNoticeUrlFormat() {
        return noticeUrlFormat;
    }

    /**
     * 话题封面图绝对路径
     * @param noticeUrlFormat the noticeUrlFormat to set
     */
    public void setNoticeUrlFormat(String noticeUrlFormat) {
        this.noticeUrlFormat = noticeUrlFormat;
    }

    /**
     * 记录Id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 记录Id
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 话题标题
     * @return noticeTitle
     */
    public String getNoticeTitle() {
        return noticeTitle;
    }

    /**
     * 话题标题
     * @param noticeTitle the noticeTitle to set
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * 话题封面图地址
     * @return noticeUrl
     */
    public String getNoticeUrl() {
        return noticeUrl;
    }

    /**
     * 话题封面图地址
     * @param noticeUrl the noticeUrl to set
     */
    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
        if (noticeUrl != null) {
            setNoticeUrlFormat(FileUrlConfig.file_visit_url + noticeUrl);
        }
    }

    /**
     * 话题内容
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 话题内容
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

    /**
     * 置顶时间
     * @return topTime
     */
    public Timestamp getTopTime() {
        return topTime;
    }

    /**
     *置顶时间
     * @param topTime the topTime to set
     */
    public void setTopTime(Timestamp topTime) {
        this.topTime = topTime;
    }
}
