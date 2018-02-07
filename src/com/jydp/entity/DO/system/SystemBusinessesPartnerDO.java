package com.jydp.entity.DO.system;

import config.FileUrlConfig;

import java.sql.Timestamp;

/**
 * 合作商家
 *
 * @author zym
 */
public class SystemBusinessesPartnerDO {

    private int id; //记录Id
    private String businessesName; //商家名称
    private String businessesImageUrl; //商家图片地址
    private String webLinkUrl; //web端链接地址
    private String wapLinkUrl; //wap端链接地址
    private Timestamp addTime; //添加时间
    private Timestamp topTime; //置顶时间

    private String businessesImageUrlFormat; // 商家图片绝对地址

    /**
     * 商家图片绝对地址
     * @return businessesImageUrlFormat
     */
    public String getBusinessesImageUrlFormat() {
        return businessesImageUrlFormat;
    }

    /**
     * 商家图片绝对地址
     * @param businessesImageUrlFormat adsImageUrlFormat the businessesImageUrlFormat to set
     */
    public void setBusinessesImageUrlFormat(String businessesImageUrlFormat) {
        this.businessesImageUrlFormat = businessesImageUrlFormat;
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
     * 商家名称
     * @return businessesName
     */
    public String getBusinessesName() {
        return businessesName;
    }

    /**
     * 商家名称
     * @param businessesName the businessesName to set
     */
    public void setBusinessesName(String businessesName) {
        this.businessesName = businessesName;
    }

    /**
     * 商家图片地址
     * @return businessesImageUrl
     */
    public String getBusinessesImageUrl() {
        return businessesImageUrl;
    }

    /**
     * 商家图片地址
     * @param businessesImageUrl the businessesImageUrl to set
     */
    public void setBusinessesImageUrl(String businessesImageUrl) {
        this.businessesImageUrl = businessesImageUrl;
        if (businessesImageUrl != null) {
            setBusinessesImageUrlFormat(FileUrlConfig.file_visit_url + businessesImageUrl);
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
     * 添加时间
     * @return addTime
     */
    public Timestamp getAddTime() {
        return addTime;
    }

    /**
     *  添加时间
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
     * 置顶时间
     * @param topTime the topTime to set
     */
    public void setTopTime(Timestamp topTime) {
        this.topTime = topTime;
    }
}
