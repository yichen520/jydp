package com.jydp.service.impl.back;


import com.jydp.dao.IBackerHandleUserRecordBalanceDao;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceDO;
import com.jydp.service.IBackerHandleUserRecordBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


/**
 * 后台管理员增减用户余额记录
 * @author sy
 *
 */
@Service("backerHandleUserRecordBalanceService")
public class BackerHandleUserRecordBalanceServiceImpl implements IBackerHandleUserRecordBalanceService {

    /**后台管理员增减用户余额记录*/
    @Autowired
    private IBackerHandleUserRecordBalanceDao backerHandleUserRecordBalanceDao;

    /**
     * 新增 后台管理员增减用户余额记录
     * @param backerHandleUserRecordBalanceDO 待新增的 后台管理员增减用户余额记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserRecordBalance(
            BackerHandleUserRecordBalanceDO backerHandleUserRecordBalanceDO){
        return backerHandleUserRecordBalanceDao.insertBackerHandleUserRecordBalance(backerHandleUserRecordBalanceDO);
    }

    /**
     * 根据筛选条件获取后台增减用户余额的记录
     * @param userAccount  用户账号
     * @param typeHandle  操作类型
     * @param backerAccount  操作管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param pageNumber 查询的页码
     * @param pageSize 每页的信息数量
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    public List<BackerHandleUserRecordBalanceDO> getUserRecordBalanceList(String userAccount, int typeHandle, String backerAccount,
                                                                          Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize){
        return backerHandleUserRecordBalanceDao.getUserRecordBalanceList(userAccount, typeHandle, backerAccount, startAddTime, endAddTime,
                pageNumber, pageSize);
    }

    /**
     * 根据筛选条件获取后台增减用户余额的记录数量
     * @param userAccount  用户账号
     * @param typeHandle  操作类型
     * @param backerAccount  操作管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @return  操作成功：返回记录集合数量，操作失败：返回0
     */
    public int getUserRecordBalanceNumber(String userAccount, int typeHandle, String backerAccount,
                                                                     Timestamp startAddTime, Timestamp endAddTime){
        return backerHandleUserRecordBalanceDao.getUserRecordBalanceNumber(userAccount, typeHandle, backerAccount, startAddTime, endAddTime);
    }
}
