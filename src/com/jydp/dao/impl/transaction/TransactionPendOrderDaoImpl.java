package com.jydp.dao.impl.transaction;

import com.jydp.dao.ITransactionPendOrderDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 挂单记录
 * @author fk
 *
 */
@Repository
public class TransactionPendOrderDaoImpl implements ITransactionPendOrderDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
