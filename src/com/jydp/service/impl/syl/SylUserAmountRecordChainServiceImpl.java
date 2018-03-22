package com.jydp.service.impl.syl;

import com.jydp.dao.ISylUserAmountRecordChainDao;
import com.jydp.entity.DO.syl.SylUserAmountRecordChainDO;
import com.jydp.service.ISylUserAmountRecordChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JYDP转账盛源链记录(JYDP-->SYL)
 *
 * @author sy
 */
@Service("sylUserAmountRecordChainService")
public class SylUserAmountRecordChainServiceImpl implements ISylUserAmountRecordChainService {

    /** JYDP转账盛源链记录(JYDP-->SYL) */
    @Autowired
    private ISylUserAmountRecordChainDao sylUserAmountRecordChainDao;

    /**
     * 新增 JYDP转账盛源链记录(JYDP-->SYL)
     * @param sylUserAmountRecordChain 待新增的 JYDP转账盛源链记录(JYDP-->SYL)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylUserAmountRecordChain(SylUserAmountRecordChainDO sylUserAmountRecordChain){
        return sylUserAmountRecordChainDao.insertSylUserAmountRecordChain(sylUserAmountRecordChain);
    }
}
