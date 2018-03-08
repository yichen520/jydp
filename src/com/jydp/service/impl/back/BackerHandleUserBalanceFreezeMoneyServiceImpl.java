package com.jydp.service.impl.back;

import com.jydp.dao.IBackerHandleUserBalanceFreezeMoneyDao;
import com.jydp.dao.IBackerHandleUserBalanceMoneyDao;
import com.jydp.entity.DO.back.BackerHandleUserBalanceFreezeMoneyDO;
import com.jydp.service.IBackerHandleUserBalanceFreezeMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台管理员增减用户冻结币记录
 * @author sy
 */
@Service("backerHandleUserBalanceFreezeMoneyService")
public class BackerHandleUserBalanceFreezeMoneyServiceImpl implements IBackerHandleUserBalanceFreezeMoneyService {

    /**后台管理员增减用户冻结币记录*/
    @Autowired
    private IBackerHandleUserBalanceFreezeMoneyDao backerHandleUserBalanceFreezeMoneyDao;

    /**
     * 新增 后台管理员增减用户冻结币记录
     * @param backerHandleUserBalanceFreezeMoney 待新增的 后台管理员增减用户冻结币记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserBalanceFreezeMoney(BackerHandleUserBalanceFreezeMoneyDO backerHandleUserBalanceFreezeMoney){
        return backerHandleUserBalanceFreezeMoneyDao.insertBackerHandleUserBalanceFreezeMoney(backerHandleUserBalanceFreezeMoney);
    }
}
