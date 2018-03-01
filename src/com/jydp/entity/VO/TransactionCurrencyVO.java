package com.jydp.entity.VO;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import config.FileUrlConfig;

/**
 * 交易币种
 *
 * @author fk
 */
public class TransactionCurrencyVO extends TransactionCurrencyDO {

    private String currencyImg;  //币种徽标
    private String currencyImgUrl;  //币种徽标图片绝对地址


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
}
