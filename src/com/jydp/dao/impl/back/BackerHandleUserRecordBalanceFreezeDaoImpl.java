package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IBackerHandleUserRecordBalanceFreezeDao;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceFreezeDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 后台管理员增减用户冻结余额记录
 * @author sy
 */
@Repository
public class BackerHandleUserRecordBalanceFreezeDaoImpl implements IBackerHandleUserRecordBalanceFreezeDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 后台管理员增减用户冻结余额记录
     * @param backerHandleUserRecordBalanceFreeze 待新增的 后台管理员增减用户冻结余额记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserRecordBalanceFreeze(BackerHandleUserRecordBalanceFreezeDO backerHandleUserRecordBalanceFreeze){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("BackerHandleUserRecordBalanceFreeze_insertBackerHandleUserRecordBalanceFreeze",
                    backerHandleUserRecordBalanceFreeze);
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
