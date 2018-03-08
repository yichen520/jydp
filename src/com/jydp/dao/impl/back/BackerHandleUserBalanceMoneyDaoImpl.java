package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IBackerHandleUserBalanceMoneyDao;
import com.jydp.entity.DO.back.BackerHandleUserBalanceMoneyDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 后台管理员增减用户可用币记录
 * @author sy
 */
@Repository
public class BackerHandleUserBalanceMoneyDaoImpl implements IBackerHandleUserBalanceMoneyDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 后台管理员增减用户可用币记录
     * @param backerHandleUserBalanceMoney 待新增的 后台管理员增减用户可用币记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserBalanceMoney(BackerHandleUserBalanceMoneyDO backerHandleUserBalanceMoney){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("BackerHandleUserBalanceMoney_insertBackerHandleUserBalanceMoney",
                    backerHandleUserBalanceMoney);
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
