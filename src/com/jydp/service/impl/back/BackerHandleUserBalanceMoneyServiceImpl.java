package com.jydp.service.impl.back;

import com.jydp.dao.IBackerHandleUserBalanceMoneyDao;
import com.jydp.entity.DO.back.BackerHandleUserBalanceMoneyDO;
import com.jydp.service.IBackerHandleUserBalanceMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台管理员增减用户可用币记录
 * @author sy
 */
@Service("backerHandleUserBalanceMoneyService")
public class BackerHandleUserBalanceMoneyServiceImpl implements IBackerHandleUserBalanceMoneyService {
    /**后台管理员增减用户余额记录*/
    @Autowired
    private IBackerHandleUserBalanceMoneyDao backerHandleUserBalanceMoneyDao;

    /**
     * 新增 后台管理员增减用户可用币记录
     * @param backerHandleUserBalanceMoney 待新增的 后台管理员增减用户可用币记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserBalanceMoney(BackerHandleUserBalanceMoneyDO backerHandleUserBalanceMoney){
        return backerHandleUserBalanceMoneyDao.insertBackerHandleUserBalanceMoney(backerHandleUserBalanceMoney);
    }
}
