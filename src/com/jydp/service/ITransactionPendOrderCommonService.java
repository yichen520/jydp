package com.jydp.service;

/**
 * 从数据库拉取 挂单记录 到redis
 * @author hz
 *
 */
public interface ITransactionPendOrderCommonService {
    /**
     * 从数据库拉取 挂单记录,买一价，卖一价 到redis
     */
    void getPendOrder();

}
