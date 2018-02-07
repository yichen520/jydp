package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionUserDealDao;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.service.ITransactionUserDealService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 成交记录
 * @author fk
 *
 */
@Service("transactionUserDealService")
public class TransactionUserDealServiceImpl implements ITransactionUserDealService{

    /** 成交记录 */
    @Autowired
    private ITransactionUserDealDao transactionUserDealDao;

    /**
     * 查询用户成交记录
     * @param userId 用户Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDO> getTransactionUserDeallist(int userId) {
        return transactionUserDealDao.getTransactionUserDeallist(userId);
    }
}
