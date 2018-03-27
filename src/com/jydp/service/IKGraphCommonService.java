package com.jydp.service;

/**
 * k线图统计数据公共服务
 * @author whx
 */
public interface IKGraphCommonService {

    /**
     * k线图统计（定时器，redis操作）
     */
    void exeKLineGraphForTimer();

}
