package com.jydp.dao;

import com.jydp.entity.DO.syl.SylToJydpChainDO;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
public interface ISylToJydpChainDao {

    /**
     * 新增 SYL转账盛源链记录(SYL-->JYDP)
     * @param sylToJydpChain 待新增的 SYL转账盛源链记录(SYL-->JYDP)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertSylToJydpChain(SylToJydpChainDO sylToJydpChain);

    /**
     * 根据订单号查询订单信息
     * @param sylRecordNo 订单号
     * @return 查询成功：返回订单信息, 查询失败或者没有相关信息：返回null
     */
    SylToJydpChainDO getSylToJydpChainBysylRecordNo(String sylRecordNo);
}
