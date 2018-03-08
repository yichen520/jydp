package com.jydp.dao;

import com.jydp.entity.DO.back.BackerHandleUserBalanceMoneyDO;
import com.jydp.entity.VO.BackerHandleUserBalanceMoneyVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 后台管理员增减用户可用币记录
 * @author sy
 */
public interface IBackerHandleUserBalanceMoneyDao {

    /**
     * 新增 后台管理员增减用户可用币记录
     * @param backerHandleUserBalanceMoney 待新增的 后台管理员增减用户可用币记录
     * @return 新增成功：true，新增失败：返回false
     */
    boolean insertBackerHandleUserBalanceMoney(BackerHandleUserBalanceMoneyDO backerHandleUserBalanceMoney);

    /**
     * 根据筛选条件获取后台增减用户可用币的记录
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
    List<BackerHandleUserBalanceMoneyVO> getUserRecordBalanceList(String userAccount, int typeHandle,int currencyId, String backerAccount,
                                                                  Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize);

    /**
     * 根据筛选条件获取后台增减用户可用币的记录数量
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
