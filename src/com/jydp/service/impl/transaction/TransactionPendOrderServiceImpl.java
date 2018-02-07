package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionPendOrderDao;
import com.jydp.service.ITransactionPendOrderService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 挂单记录
 * @author fk
 *
 */
@Service("transactionPendOrderService")
public class TransactionPendOrderServiceImpl implements ITransactionPendOrderService{

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderDao transactionPendOrderDao;
}
