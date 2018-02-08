package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionUserDealDao;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 成交记录
 * @author fk
 *
 */
@Repository
public class TransactionUserDealDaoImpl implements ITransactionUserDealDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询用户成交记录
     * @param userId 用户Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDO> getTransactionUserDeallist(int userId) {
        List<TransactionUserDealDO> transactionUserDealList = null;

        try {
            transactionUserDealList = sqlSessionTemplate.selectList("TransactionUserDeal_getTransactionUserDeallist",userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionUserDealList;
    }
}
