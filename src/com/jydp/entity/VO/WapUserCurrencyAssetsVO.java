package com.jydp.entity.VO;

/**
 * wap端用户币种资产
 * Created by xushilong3623 on 2018/3/26.
 */
public class WapUserCurrencyAssetsVO {

    /**
     * 币种id
     */
    private Integer currencyId;
    /**
     * 货币名称
     */
    private String currencyName;

    /**
     * 货币简写名称
     */
    private String currencyShortName;
    /**
     * 货币数量
     */
    private Double currencyNumber;
    /**
     * 冻结数量
     */
    private Double currencyNumberLock;

    /**
     * 币种总资产
     */
    private Double totalCurrencyAssets;

    /**
     * 币种图片地址
     */
    private String currencyImg;
    /**
     * 币种id
     */
    public Integer getCurrencyId() {
        return currencyId;
    }
    /**
     * 币种id
     */
    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }
    /**
     * 货币名称
     */
    public String getCurrencyName() {
        return currencyName;
    }
    /**
     * 货币名称
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    /**
     * 货币数量
     */
    public Double getCurrencyNumber() {
        return currencyNumber;
    }
    /**
     * 货币数量
     */
    public void setCurrencyNumber(Double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }
    /**
     * 冻结数量
     */
    public Double getCurrencyNumberLock() {
        return currencyNumberLock;
    }
    /**
     * 冻结数量
     */
    public void setCurrencyNumberLock(Double currencyNumberLock) {
        this.currencyNumberLock = currencyNumberLock;
    }
    /**
     * 币种总资产
     */
    public Double getTotalCurrencyAssets() {
        return totalCurrencyAssets;
    }
    /**
     * 币种总资产
     */
    public void setTotalCurrencyAssets(Double totalCurrencyAssets) {
        this.totalCurrencyAssets = totalCurrencyAssets;
    }

    /**
     * 货币简写名称
     */
    public String getCurrencyShortName() {
        return currencyShortName;
    }
    /**
     * 货币简写名称
     */
    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }
    /**
     * 币种图片地址
     */
    public String getCurrencyImg() {
        return currencyImg;
    }
    /**
     * 币种图片地址
     */
    public void setCurrencyImg(String currencyImg) {
        this.currencyImg = currencyImg;
    }
}
