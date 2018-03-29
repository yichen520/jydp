package com.jydp.service;

import com.jydp.entity.DO.back.BackerHandleUserBalanceFreezeMoneyDO;
import com.jydp.entity.VO.BackerHandleUserBalanceFreezeMoneyVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * JYDP转账盛源链(JYDP-->SYL)
 * @author hz
 */
public interface IJydpToSylService {

    /**
     * 交易大盘向盛源链钱包转币申请
     */
    void jydpToSylApply();

}
