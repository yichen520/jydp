package com.jydp.service;

import com.jydp.entity.DO.back.BackerHandleUserBalanceFreezeMoneyDO;
import com.jydp.entity.VO.BackerHandleUserBalanceFreezeMoneyVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 后台管理员增减用户冻结币记录
 * @author sy
 */
public interface IBackerHandleUserBalanceFreezeMoneyService {

    /**
     * 新增 后台管理员增减用户冻结币记录
     * @param backerHandleUserBalanceFreezeMoney 待新增的 后台管理员增减用户冻结币记录
     * @return 新增成功：true，新增失败：返回false
     */
    boolean insertBackerHandleUserBalanceFreezeMoney(BackerHandleUserBalanceFreezeMoneyDO backerHandleUserBalanceFreezeMoney);

    /**
     * 根据筛选条件获取后台增减用户冻结币的记录
     * @param userAccount  用户账号
     * @param typeHandle  操作类型
     * @param currencyId  币种id
     * @param backerAccount  操作管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param pageNumber 查询的页码
     * @param pageSize 每页的信息数量
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    List<BackerHandleUserBalanceFreezeMoneyVO> getUserRecordBalanceList(String userAccount, int typeHandle, int currencyId, String backerAccount,
                                                                        Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize);

    /**
     * 根据筛选条件获取后台增减用户冻结币的记录数量
     * @param userAccount  用户账号
     * @param typeHandle  操作类型
     * @param currencyId  币种id
     * @param backerAccount  操作管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @return  操作成功：返回记录集合数量，操作失败：返回0
     */
    int getUserRecordBalanceNumber(String userAccount, int typeHandle, int currencyId, String backerAccount,
                                   Timestamp startAddTime, Timestamp endAddTime);
}
