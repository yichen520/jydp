package com.jydp.service.impl.otc;

import com.iqmkj.utils.DateUtil;
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
}
