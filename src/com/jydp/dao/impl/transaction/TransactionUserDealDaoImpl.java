package com.jydp.dao.impl.transaction;

import com.jydp.dao.ITransactionUserDealDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 成交记录
 * @author fk
 *
 */
@Repository
public class TransactionUserDealDaoImpl implements ITransactionUserDealDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
