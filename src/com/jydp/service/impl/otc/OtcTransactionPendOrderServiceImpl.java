package com.jydp.service.impl.otc;

import com.jydp.dao.IOtcTransactionPendOrderDao;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.service.IOtcTransactionPendOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 场外交易挂单记录
 * @author lgx
 */
@Service("otcTransactionPendOrderService")
public class OtcTransactionPendOrderServiceImpl implements IOtcTransactionPendOrderService {

    @Autowired
    private IOtcTransactionPendOrderDao otcTransactionPendOrderDao;

    /**
     * 新增场外交易挂单记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public OtcTransactionPendOrderDO insertPendOrder() {

        OtcTransactionPendOrderDO otcTransactionPendOrder = new OtcTransactionPendOrderDO();
        otcTransactionPendOrderDao.insertOtcTransactionPendOrder(otcTransactionPendOrder);
     return null;
    }

    /**
     * 根据记录号查询挂单记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionPendOrderDO getOtcTransactionPendOrderByOrderNo(String orderNo) {
        return otcTransactionPendOrderDao.getOtcTransactionPendOrderByOrderNo(orderNo);
    }
}
