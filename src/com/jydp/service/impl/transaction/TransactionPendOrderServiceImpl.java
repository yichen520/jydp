package com.jydp.service.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.ITransactionPendOrderDao;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.*;
import config.SystemCommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.List;

/**
 * 挂单记录
 * @author hz
 *
 */
@Service("transactionPendOrderService")
public class TransactionPendOrderServiceImpl implements ITransactionPendOrderService{

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderDao transactionPendOrderDao;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /** 用户货币记录 */
    @Autowired
    private IUserCurrencyService userCurrencyService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 成交记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    /**
     * 新增挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param userId 用户Id
     * @param paymentType 收支类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param pendingPrice 挂单单价
     * @param pendingNumber 挂单数量
     * @param dealNumber 成交数量
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
     * @param remark 备注
     * @param addTime 添加时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertPendOrder(String pendingOrderNo, int userId, int paymentType, int currencyId,
                                   String currencyName, double pendingPrice, double pendingNumber,
                                   double dealNumber, int pendingStatus, String remark, Timestamp addTime){
        TransactionPendOrderDO transactionPendOrder = new TransactionPendOrderDO();
        transactionPendOrder.setPendingOrderNo(pendingOrderNo);
        transactionPendOrder.setUserId(userId);
        transactionPendOrder.setPaymentType(paymentType);
        transactionPendOrder.setCurrencyId(currencyId);
        transactionPendOrder.setCurrencyName(currencyName);
        transactionPendOrder.setPendingPrice(pendingPrice);
        transactionPendOrder.setPendingNumber(pendingNumber);
        transactionPendOrder.setDealNumber(dealNumber);
        transactionPendOrder.setPendingStatus(pendingStatus);
        transactionPendOrder.setRemark(remark);
        transactionPendOrder.setAddTime(addTime);

        return transactionPendOrderDao.insertPendOrder(transactionPendOrder);
    }

    /**
     * 修改挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param dealNumber 成交数量
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
     * @param remark 备注
     * @param endTime 完成时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePendOrder(String pendingOrderNo, double dealNumber, int pendingStatus, String remark, Timestamp endTime){
        TransactionPendOrderDO transactionPendOrder = new TransactionPendOrderDO();
        transactionPendOrder.setPendingOrderNo(pendingOrderNo);
        transactionPendOrder.setDealNumber(dealNumber);
        transactionPendOrder.setPendingStatus(pendingStatus);
        transactionPendOrder.setRemark(remark);
        transactionPendOrder.setEndTime(endTime);

        return transactionPendOrderDao.updatePendOrder(transactionPendOrder);
    }

    /**
     * 根据挂单记录号查询挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return 操作成功：返回挂单记录，操作失败：返回null
     */
    public TransactionPendOrderDO getPendOrderByPendingOrderNo(String pendingOrderNo){
        return transactionPendOrderDao.getPendOrderByPendingOrderNo(pendingOrderNo);
    }

    /**
     * 根据用户id查询挂单记录个数
     * @param userId 用户Id
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    public int countPendOrderByUserId(int userId){
        return transactionPendOrderDao.countPendOrderByUserId(userId);
    }

    /**
     * 根据用户id分页查询挂单记录
     * @param userId 用户Id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listPendOrderByUserId(int userId, int pageNumber, int pageSize){
        return transactionPendOrderDao.listPendOrderByUserId(userId, pageNumber, pageSize);
    }

    /**
     * 修改挂单状态
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePendingStatus(String pendingOrderNo, int pendingStatus){
        return transactionPendOrderDao.updatePendingStatus(pendingOrderNo,pendingStatus);
    }

    /**
     * 查询最近num条挂单记录（价格相同的合并，用于交易中心显示）
     * @param paymentType 交易类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param num 需要查询的条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listLatestRecords(int paymentType, int currencyId, int num){
        return transactionPendOrderDao.listLatestRecords(paymentType, currencyId, num);
    }

    /**
     * 查询挂单记录个数（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id（查全部为0）
     * @param paymentType 交易类型,1：买入，2：卖出（查全部为0）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销（查全部为0）
     * @param startAddTime 挂单起始时间，没有值填null
     * @param endAddTime 挂单结束时间，没有值填null
     * @param startFinishTime 完成起始时间，没有值填null
     * @param endFinishTime 完成结束时间，没有值填null
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    public int countPendOrderForBack(String userAccount, int currencyId, int paymentType, int pendingStatus,
                              Timestamp startAddTime, Timestamp endAddTime,
                              Timestamp startFinishTime, Timestamp endFinishTime) {
        return transactionPendOrderDao.countPendOrderForBack(userAccount, currencyId, paymentType, pendingStatus,
                startAddTime, endAddTime, startFinishTime, endFinishTime);
    }

    /**
     * 分页查询挂单记录列表（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id（查全部为0）
     * @param paymentType 交易类型,1：买入，2：卖出（查全部为0）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销（查全部为0）
     * @param startAddTime 挂单起始时间，没有值填null
     * @param endAddTime 挂单结束时间，没有值填null
     * @param startFinishTime 完成起始时间，没有值填null
     * @param endFinishTime 完成结束时间，没有值填null
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listPendOrderForBack(String userAccount, int currencyId, int paymentType,
                                                      int pendingStatus, Timestamp startAddTime, Timestamp endAddTime,
                                                      Timestamp startFinishTime, Timestamp endFinishTime,
                                                      int pageNumber, int pageSize){
        return transactionPendOrderDao.listPendOrderForBack(userAccount, currencyId, paymentType, pendingStatus, startAddTime, endAddTime,
                startFinishTime, endFinishTime, pageNumber, pageSize);
    }

    /**
     * 撤销挂单
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean revokePendOrder(String pendingOrderNo){
        //获取挂单记录
        TransactionPendOrderDO transactionPendOrder = getPendOrderByPendingOrderNo(pendingOrderNo);
        int paymentType = transactionPendOrder.getPaymentType();
        int pendingStatus = transactionPendOrder.getPendingStatus();
        int currencyId = transactionPendOrder.getCurrencyId();
        int userId = transactionPendOrder.getUserId();
        double dealNumber = transactionPendOrder.getDealNumber();
        if(pendingStatus != 1 && pendingStatus != 2){
            return false;
        }
        //计算撤销的数量
        double num = transactionPendOrder.getPendingNumber() - dealNumber;
        //业务执行状态
        boolean excuteSuccess = true;

        if(paymentType == 1){ //如果是买入
            //查询用户美金金额
            UserDO user = userService.getUserByUserId(userId);
            //判断冻结金额是否大于等于num
            num = num * transactionPendOrder.getPendingPrice() * (1+0.002);
            if(user.getUserBalanceLock() < num){
                return false;
            }
            //减少冻结数量
            if(excuteSuccess){
                excuteSuccess = userService.updateReduceUserBalanceLock(userId, num);
            }
            //增加美金数量
            if(excuteSuccess){
                excuteSuccess = userService.updateAddUserAmount(userId, num,0);
            }
            //增加美金记录
            if(excuteSuccess){
                Timestamp curTime = DateUtil.getCurrentTime();
                String orderNo = SystemCommonConfig.USER_BALANCE +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);

                UserBalanceDO userBalance = new UserBalanceDO();
                userBalance.setOrderNo(orderNo);
                userBalance.setUserId(userId);
                userBalance.setPaymentType(1);
                userBalance.setFromType("撤销挂单返还冻结美金");
                userBalance.setBalanceNumber(num);
                userBalance.setRemark("返还冻结的手续费");
                userBalance.setAddTime(curTime);

                excuteSuccess = userBalanceService.insertUserBalance(userBalance);
            }

        }else if(paymentType == 2){ //如果是卖出
            //查询用户币数量
            UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(
                    userId, currencyId);
            //判断冻结数量是否大于等于num
            if(userCurrencyNum.getCurrencyNumberLock() < num){
                return false;
            }
            //减少冻结数量
            if(excuteSuccess){
                excuteSuccess = userCurrencyNumService.reduceCurrencyNumberLock(userId, currencyId, num);
            }
            //增加币数量
            if(excuteSuccess){
                excuteSuccess = userCurrencyNumService.increaseCurrencyNumber(userId, currencyId, num);
            }
            //增加币记录
            if(excuteSuccess){
                Timestamp curTime = DateUtil.getCurrentTime();
                String orderNo = SystemCommonConfig.USER_CURRENCY +
                        DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                        NumberUtil.createNumberStr(10);

                excuteSuccess = userCurrencyService.insertUserCurrency(orderNo, currencyId,
                        1, "撤销挂单返还币", num, currencyId, "无手续费", curTime);
            }
        }

        //修改挂单状态
        if(excuteSuccess) {
            if (dealNumber > 0) {
                excuteSuccess = transactionPendOrderDao.updatePartRevoke(pendingOrderNo, num);
            } else if (dealNumber == 0) {
                excuteSuccess = transactionPendOrderDao.updateAllRevoke(pendingOrderNo, num);
            }
        }

        //增加撤单记录
        if(excuteSuccess){
            Timestamp curTime = DateUtil.getCurrentTime();
            String orderNo = SystemCommonConfig.TRANSACTION_USER_DEAL +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);

            excuteSuccess = transactionUserDealService.insertTransactionUserDeal(orderNo, "0", userId,
                    3, currencyId, transactionPendOrder.getCurrencyName(), 0, num,
                    0,"撤销挂单", curTime);
        }

        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return excuteSuccess;
    }

}
