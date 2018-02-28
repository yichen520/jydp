package com.jydp.service;

import com.jydp.entity.DO.system.SystemAccountAmountDO;

import java.util.List;


/**
 * 系统账户金额
 * @author hz
 *
 */
public interface ISystemAccountAmountService {

    /**
     * 新增 系统账户金额
     * @param systemAccountAmountDO 待新增的 系统账户金额
     * @return 新增成功：返回true，新增失败：返回false
     */
    boolean insertSystemAccountAmount(SystemAccountAmountDO systemAccountAmountDO);

    /**
     * 获取所有系统账户金额
     * @return 查询成功：返回 系统账户金额list，查询失败，返回null
     */
    List<SystemAccountAmountDO> getSystemAccountAmountAllForBack();

    /**
     * 根据系统账户编码拿到 系统账户金额
     * @param accountCode 系统账户编码
     * @return 查询成功：返回 系统账户金额，查询失败，返回null
     */
    SystemAccountAmountDO getSystemAccountAmountById(int accountCode);

    /**
     * 增加系统账户金额
     * @param accountCode 系统账户编码
     * @param amount 待增加金额
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean addSystemAccountAmount(int accountCode, double amount);

    /**
     * 修改 系统账户金额
     * @param systemAccountAmountDO 系统账户金额
     * @return 修改成功：返回true，修改失败：返回false
     */
    boolean updateSystemAccountAmount(SystemAccountAmountDO systemAccountAmountDO);

    /**
     * 删除 系统账户金额
     * @param accountCode 系统账户编码
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemAccountAmount(int accountCode);
}