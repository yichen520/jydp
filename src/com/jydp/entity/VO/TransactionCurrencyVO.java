package com.jydp.entity.VO;

import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import config.FileUrlConfig;

import java.sql.Timestamp;

/**
 * 交易币种
 *
 * @author fk
 */
public class TransactionCurrencyVO extends TransactionCurrencyDO {

    private String currencyImg;  //币种徽标
    private String currencyImgUrl;  //币种徽标图片绝对地址

    private String upTimeStr;  //添加时间字符串
    private Timestamp upTime;  //添加时间
    /**
     * 币种徽标
     * @return the currencyImg
     */
    public String getCurrencyImg() {
        return currencyImg;
    }


    /**
     * 币种徽标
     * @param currencyImg the currencyImg to set
     */
    public void setCurrencyImg(String currencyImg) {
        this.currencyImg = currencyImg;
        if (currencyImg != null) {
            setCurrencyImgUrl(FileUrlConfig.file_visit_url + currencyImg);
        }
    }

    /**
     * 币种徽标图片绝对地址
     * @return the currencyImgUrl
     */
    public String getCurrencyImgUrl() {
        return currencyImgUrl;
    }

    /**
     * 币种徽标图片绝对地址
     * @param currencyImgUrl the currencyImgUrl to set
     */
    public void setCurrencyImgUrl(String currencyImgUrl) {
        this.currencyImgUrl = currencyImgUrl;
    }

    /**
     * 上线时间Str
     * @return the upTimeStr
     */
    public String getUpTimeStr() {
        return upTimeStr;
    }

    /**
     * 上线时间Str
     * @param upTimeStr the upTimeStr to set
     */
    public void setUpTimeStr(String upTimeStr) {
        this.upTimeStr = upTimeStr;
    }

    /**
     * 上线时间
     * @return the upTime
     */
    public Timestamp getUpTime() {
        return upTime;
    }

    /**
     * 上线时间
     * @param upTime the upTime to set
     */
    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
        if (upTime != null) {
            setUpTimeStr(DateUtil.longToTimeStr(upTime.getTime(), DateUtil.dateFormat2));
        }
    }
}
