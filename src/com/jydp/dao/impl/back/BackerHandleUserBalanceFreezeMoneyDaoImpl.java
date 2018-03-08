package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IBackerHandleUserBalanceFreezeMoneyDao;
import com.jydp.entity.DO.back.BackerHandleUserBalanceFreezeMoneyDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 后台管理员增减用户冻结币记录
 * @author sy
 */
@Repository
public class BackerHandleUserBalanceFreezeMoneyDaoImpl implements IBackerHandleUserBalanceFreezeMoneyDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 后台管理员增减用户冻结币记录
     * @param backerHandleUserBalanceFreezeMoney 待新增的 后台管理员增减用户冻结币记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserBalanceFreezeMoney(BackerHandleUserBalanceFreezeMoneyDO backerHandleUserBalanceFreezeMoney){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("BackerHandleUserBalanceFreezeMoney_insertBackerHandleUserBalanceFreezeMoney",
                    backerHandleUserBalanceFreezeMoney);
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
