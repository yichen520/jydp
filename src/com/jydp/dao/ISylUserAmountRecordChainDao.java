package com.jydp.dao;


import com.jydp.entity.DO.syl.SylUserAmountRecordChainDO;

/**
 * JYDP转账盛源链记录(JYDP-->SYL)
 *
 * @author sy
 */
public interface ISylUserAmountRecordChainDao {

    /**
     * 新增 JYDP转账盛源链记录(JYDP-->SYL)
     * @param sylUserAmountRecordChain 待新增的 JYDP转账盛源链记录(JYDP-->SYL)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertSylUserAmountRecordChain(SylUserAmountRecordChainDO sylUserAmountRecordChain);
}
