package com.jydp.entity.DTO;

/**
 * 查询基准货币价格信息
 *
 * @author sy
 */
public class TransactionDealPriceDTO {
    private int currencyId; //币种id
    private double transactionPrice; //基准价格 （当前交易价，总价，最高价，最低价，成交量，成交额）
    private String currencyShortName; //货币简称

    /**
     * 币种id
     *
     * @return the currency id
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * 币种id
     *
     * @param currencyId the currency id
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 基准价格 （当前交易价，总价，最高价，最低价，成交量，成交额）
     *
     * @return the transaction price
     */
    public double getTransactionPrice() {
        return transactionPrice;
    }

    /**
     * 基准价格 （当前交易价，总价，最高价，最低价，成交量，成交额）
     *
     * @param transactionPrice the transaction price
     */
    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    /**
     * 货币简称
     *
     * @return the currency short name
     */
    public String getCurrencyShortName() {
        return currencyShortName;
    }

    /**
     * 货币简称
     *
     * @param currencyShortName the currency short name
     */
    public void setCurrencyShortName(String currencyShortName) {
        this.currencyShortName = currencyShortName;
    }
}
