package com.jydp.entity.DO.user;


import java.sql.Timestamp;

/**
 * 用户认证详情图
 *
 * @author hht
 */
public class UserIdentificationImageDO {

    private long id;  //记录Id
    private long identificationId;  //用户认证Id
    private String imageUrl;  //用户认证详情图地址
    private Timestamp addTime;  //添加时间


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
    public void setId(long id) {
        this.id = id;
    }


    /**
     * 用户认证Id
     *
     * @return the identification id
     */
    public long getIdentificationId() {
        return identificationId;
    }

    /**
     * 用户认证Id
     *
     * @param identificationId the identification id
     */
    public void setIdentificationId(long identificationId) {
        this.identificationId = identificationId;
    }


    /**
     * 用户认证详情图地址
     *
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 用户认证详情图地址
     *
     * @param imageUrl the image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    /**
     * 添加时间
     *
     * @return the add time
     */
    public java.sql.Timestamp getAddTime() {
        return addTime;
    }

    /**
     * 添加时间
     *
     * @param addTime the add time
     */
    public void setAddTime(java.sql.Timestamp addTime) {
        this.addTime = addTime;
    }

}
