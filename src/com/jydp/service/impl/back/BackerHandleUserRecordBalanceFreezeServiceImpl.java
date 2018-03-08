package com.jydp.service.impl.back;

import com.jydp.dao.IBackerHandleUserRecordBalanceFreezeDao;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceFreezeDO;
import com.jydp.service.IBackerHandleUserRecordBalanceFreezeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台管理员增减用户冻结余额记录
 * @author sy
 */
@Service("backerHandleUserRecordBalanceFreezeService")
public class BackerHandleUserRecordBalanceFreezeServiceImpl implements IBackerHandleUserRecordBalanceFreezeService {

    /**后台管理员增减用户冻结余额记录*/
    @Autowired
    private IBackerHandleUserRecordBalanceFreezeDao backerHandleUserRecordBalanceFreezeDao;

    /**
     * 新增 后台管理员增减用户冻结余额记录
     * @param backerHandleUserRecordBalanceFreeze 待新增的 后台管理员增减用户冻结余额记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserRecordBalanceFreeze(BackerHandleUserRecordBalanceFreezeDO backerHandleUserRecordBalanceFreeze){
        return backerHandleUserRecordBalanceFreezeDao.insertBackerHandleUserRecordBalanceFreeze(backerHandleUserRecordBalanceFreeze);
    }
}
