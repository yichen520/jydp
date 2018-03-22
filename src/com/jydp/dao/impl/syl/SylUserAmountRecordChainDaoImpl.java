package com.jydp.dao.impl.syl;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISylUserAmountRecordChainDao;
import com.jydp.entity.DO.syl.SylUserAmountRecordChainDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * JYDP转账盛源链记录(JYDP-->SYL)
 *
 * @author sy
 */
@Repository
public class SylUserAmountRecordChainDaoImpl implements ISylUserAmountRecordChainDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 JYDP转账盛源链记录(JYDP-->SYL)
     * @param sylUserAmountRecordChain 待新增的 JYDP转账盛源链记录(JYDP-->SYL)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylUserAmountRecordChain(SylUserAmountRecordChainDO sylUserAmountRecordChain){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SylUserAmountRecordChain_insertSylUserAmountRecordChain", sylUserAmountRecordChain);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
