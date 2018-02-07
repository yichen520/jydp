package com.jydp.entity.DO.system;

import config.FileUrlConfig;

import java.sql.Timestamp;

/**
 * 首页广告
 * @author zym
 *
 */
public class SystemAdsHomepagesDO {

    private int id; //记录Id
    private String adsTitle; //广告标题
    private String adsImageUrl; //广告图片地址
    private String webLinkUrl; //web端链接地址
    private String wapLinkUrl; //wap端链接地址
    private int rankNumber; //排名位置
    private Timestamp addTime; //添加时间

    private String adsImageUrlFormat; // 广告图片绝对地址

    /**
     * 广告图片绝对地址
     * @return adsImageUrlFormat
     */
    public String getAdsImageUrlFormat() {
        return adsImageUrlFormat;
    }

    /**
     * 广告图片绝对地址
     * @param adsImageUrlFormat the adsImageUrlFormat to set
     */
    public void setAdsImageUrlFormat(String adsImageUrlFormat) {
        this.adsImageUrlFormat = adsImageUrlFormat;
    }

    /**
     * 记录Id
     * return id
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
     * 广告标题
     * @return adsTitle
     */
    public String getAdsTitle() {
        return adsTitle;
    }

    /**
     * 广告标题
     * @param adsTitle the adsTitle to set
     */
    public void setAdsTitle(String adsTitle) {
        this.adsTitle = adsTitle;
    }

    /**
     * 广告图片地址
     * @return adsImageUrl
     */
    public String getAdsImageUrl() {
        return adsImageUrl;
    }

    /**
     * 广告图片地址
     * @param adsImageUrl the adsImageUrl to set
     */
    public void setAdsImageUrl(String adsImageUrl) {
        this.adsImageUrl = adsImageUrl;
        if (adsImageUrl != null) {
            setAdsImageUrlFormat(FileUrlConfig.file_visit_url + adsImageUrl);
        }
    }

    /**
     * web端链接地址
     * @return webLinkUrl
     */
    public String getWebLinkUrl() {
        return webLinkUrl;
    }

    /**
     * web端链接地址
     * @param webLinkUrl the webLinkUrl to set
     */
    public void setWebLinkUrl(String webLinkUrl) {
        this.webLinkUrl = webLinkUrl;
    }

    /**
     * wap端链接地址
     * @return wapLinkUrl
     */
    public String getWapLinkUrl() {
        return wapLinkUrl;
    }

    /**
     * wap端链接地址
     * @param wapLinkUrl the wapLinkUrl to set
     */
    public void setWapLinkUrl(String wapLinkUrl) {
        this.wapLinkUrl = wapLinkUrl;
    }

    /**
     * 排名位置
     * @return rankNumber
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
