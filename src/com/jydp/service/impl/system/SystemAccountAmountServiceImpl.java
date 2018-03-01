package com.jydp.service.impl.system;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.ISystemAccountAmountDao;
import com.jydp.entity.DO.system.SystemAccountAmountDO;
import com.jydp.service.ISystemAccountAmountService;
import config.SystemAccountAmountConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统账户金额
 * @author hz
 *
 */
@Service("systemAccountAmountService")
public class SystemAccountAmountServiceImpl implements ISystemAccountAmountService {

    /**系统账户金额 */
    @Autowired
    private ISystemAccountAmountDao systemAccountAmountDao;

    /**
     * 新增 系统账户金额
     * @param systemAccountAmountDO 待新增的 系统账户金额
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertSystemAccountAmount(SystemAccountAmountDO systemAccountAmountDO) {
        return systemAccountAmountDao.insertSystemAccountAmount(systemAccountAmountDO);
    }

    /**
     * 获取所有系统账户金额
     * @return 查询成功：返回 系统账户金额list，查询失败，返回null
     */
    public List<SystemAccountAmountDO> getSystemAccountAmountAllForBack() {
        return systemAccountAmountDao.getSystemAccountAmountAllForBack();
    }

    /**
     * 根据系统账户编码拿到 系统账户金额
     * @param accountCode 系统账户编码
     * @return 查询成功：返回 系统账户金额，查询失败，返回null
     */
    public SystemAccountAmountDO getSystemAccountAmountById(int accountCode) {
        return systemAccountAmountDao.getSystemAccountAmountById(accountCode);
    }

    /**
     * 增加系统账户金额
     * @param accountCode 系统账户编码
     * @param amount 待增加金额
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean addSystemAccountAmount(int accountCode, double amount) {
        boolean addRsult = systemAccountAmountDao.addSystemAccountAmount(accountCode, amount);
        if (addRsult) {
            return addRsult;
        }

        SystemAccountAmountDO systemAccountAmountDO = new SystemAccountAmountDO();
        systemAccountAmountDO.setAccountCode(accountCode);
        systemAccountAmountDO.setAccountAmount(amount);
        systemAccountAmountDO.setAccountName(SystemAccountAmountConfig.systemAccountMap.get(accountCode));
        systemAccountAmountDO.setAddTime(DateUtil.getCurrentTime());

        return insertSystemAccountAmount(systemAccountAmountDO);
    }

    /**
     * 修改 系统账户金额
     * @param systemAccountAmountDO 系统账户金额
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemAccountAmount(SystemAccountAmountDO systemAccountAmountDO) {
        return systemAccountAmountDao.updateSystemAccountAmount(systemAccountAmountDO);
    }

    /**
     * 删除 系统账户金额
     * @param accountCode 系统账户编码
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemAccountAmount(int accountCode) {
        return systemAccountAmountDao.deleteSystemAccountAmount(accountCode);
    }
}
