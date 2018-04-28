package com.jydp.entity.VO;

import java.io.Serializable;

/**
 * web2.0交易中心接受前端传递参数的vo对象
 * @author njx
 **/
public class TransactionCurrencyParametersVO implements Serializable {
    private static final long serialVersionUID = -6849794470754667721L; //seralizable序列号
    private String currencyId; //币种id
    private String node; //k线图刷新的节点
    private String rememberPwd; //密码
    private String payPasswordStatus; //记住密码状态

    /**
     * 密码
     */
    public String getRememberPwd() {
        return rememberPwd;
    }

    /**
     * 密码
     */
    public void setRememberPwd(String rememberPwd) {
        this.rememberPwd = rememberPwd;
    }

    /**
     * 记住密码状态 1.每次交易都输入 2.每次登陆只输入一次
     */
    public String getPayPasswordStatus() {
        return payPasswordStatus;
    }

    /**
     * 记住密码状态 1.每次交易都输入 2.每次登陆只输入一次
     */
    public void setPayPasswordStatus(String payPasswordStatus) {
        this.payPasswordStatus = payPasswordStatus;
    }

    /**
     * 获取币种id
     * @return 币种id
     */
    public String getCurrencyId() {
        return currencyId;
    }

    /**
     * 返回币种id
     * @param currencyId 币种id
     */
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * k线图时间节点
     * @return k线图时间节点 eg:5m 15m 30m
     */
    public String getNode() {
        return node;
    }

    /**
     * 设置k线图时间节点
     * @param node 时间节点
     */
    public void setNode(String node) {
        this.node = node;
    }
}
