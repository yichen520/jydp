package com.jydp.service.impl.transfer;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.IUserBalanceService;
import com.jydp.service.IUserCurrencyNumService;
import config.SystemCommonConfig;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.*;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
@Service("jydpUserCoinOutRecordService")
public class JydpUserCoinOutRecordServiceImpl implements IJydpUserCoinOutRecordService {

    /**  JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordDao jydpUserCoinOutRecordDao;

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** JYDP币种转出管理 */
    @Autowired
    private IJydpCoinConfigService jydpCoinConfigService;

    /**
     * 根据用户Id查询用户币种转出记录
     * @param userId 用户Id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    @Override
    public List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(int userId, int pageNumber, int pageSize) {
        return jydpUserCoinOutRecordDao.getJydpUserCoinOutRecordlist(userId,pageNumber,pageSize);
    }

    /**
     * 查询用户币种转出记录总数
     * @param userId 用户Id
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    @Override
    public int countJydpUserCoinOutRecord(int userId) {
        return jydpUserCoinOutRecordDao.countJydpUserCoinOutRecord(userId);
    }

    /**
     * 撤销用户币种转出记录
     * @param userId 用户Id
     * @param coinRecordNo 转出记录流水号
     * @return 操作成功：返回true；操作失败：返回false
     */
    @Override
    @Transactional
    public boolean withdrawUserCoinOutRecord(int userId,String coinRecordNo) {

        //查询用户信息
        UserDO user = userService.getUserByUserId(userId);
        if(user == null){
            return false;
        }
        //根据转出流水号查询记录信息
        JydpUserCoinOutRecordDO jydpUserCoinOutRecord = jydpUserCoinOutRecordDao.getJydpUserCoinOutRecordByRecordNo(coinRecordNo);

        if (jydpUserCoinOutRecord == null || jydpUserCoinOutRecord.getHandleStatus() != 1) {
            return false;
        }

        //业务执行状态
        boolean excuteSuccess = true;
        Timestamp curTime = DateUtil.getCurrentTime();
        String currencyName = jydpUserCoinOutRecord.getCurrencyName();
        int currencyId = jydpUserCoinOutRecord.getCurrencyId();
        double tradePriceSum = jydpUserCoinOutRecord.getCurrencyNumber();
        if (excuteSuccess) {
            //撤销用户币种提现申请
            excuteSuccess = jydpUserCoinOutRecordDao.withdrawUserCoinOutRecord(coinRecordNo);
        }

        if (excuteSuccess) {
            //给用户账户记录表添加记录
            String orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            String remark = "撤销" + currencyName + "提币申请，返还申请币";

            UserBalanceDO userBalance = new UserBalanceDO();
            userBalance.setOrderNo(orderNo);
            userBalance.setUserId(userId);
            userBalance.setCurrencyId(currencyId);
            userBalance.setCurrencyName(currencyName);
            userBalance.setFromType(UserBalanceConfig.COIN_WITHDRAWAL);
            userBalance.setBalanceNumber(tradePriceSum);
            userBalance.setFrozenNumber(0);
            userBalance.setRemark(remark);
            userBalance.setAddTime(curTime);
            excuteSuccess = userBalanceService.insertUserBalance(userBalance);
        }

        if (excuteSuccess) {
            //增加用户币数量
            excuteSuccess = userCurrencyNumService.increaseCurrencyNumber(userId,currencyId,tradePriceSum);
        }

        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return excuteSuccess;
    }

    /**
     * 用户币种提现记录
     * @param currencyId 币种id
     * @param currencyName 币种名称
     * @param userId 用户id
     * @param userAccount 用户帐号
     * @param userSylAccount 电子钱包帐户
     * @param number 提币数量
     * @return 操作成功:返回true, 操作失败:返回false
     */
    @Transactional
    public boolean insertJydpUserCoinOutRecord(int currencyId, String currencyName, int userId, String userAccount, String userSylAccount, double number) {
        //查询用户币数量
        UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(userId, currencyId);
        JydpCoinConfigDO jydpCoinConfig = jydpCoinConfigService.getJydpCoinConfigByCurrencyId(currencyId);
        if(userCurrencyNum == null || jydpCoinConfig == null){
            return false;
        }

        Timestamp curTime = DateUtil.getCurrentTime();
        String orderNo = "";
        String remark = "";

        //减少用户币种数量
        boolean excuteSuccess = userCurrencyNumService.reduceCurrencyNumber(userId, currencyId, number);

        if (excuteSuccess) {
            orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            remark = "币种提现" + number + "，扣除数量";

            UserBalanceDO userBalance = new UserBalanceDO();
            userBalance.setOrderNo(orderNo);
            userBalance.setUserId(userId);
            userBalance.setFromType(UserBalanceConfig.COIN_WITHDRAWAL);
            userBalance.setCurrencyId(currencyId);
            userBalance.setCurrencyName(currencyName);
            userBalance.setBalanceNumber(-number);
            userBalance.setFrozenNumber(0);
            userBalance.setRemark(remark);
            userBalance.setAddTime(curTime);

            //新增用户帐户记录
            excuteSuccess = userBalanceService.insertUserBalance(userBalance);
        }

        orderNo = SystemCommonConfig.COIN_OUT +
                DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(7);
        remark = currencyName + ": 币种提现" + number;

        JydpUserCoinOutRecordDO  jydpUserCoinOutRecord = new JydpUserCoinOutRecordDO();
        jydpUserCoinOutRecord.setCoinRecordNo(orderNo );
        jydpUserCoinOutRecord.setCurrencyId(currencyId);
        jydpUserCoinOutRecord.setUserId(userId );
        jydpUserCoinOutRecord.setUserAccount(userAccount);
        jydpUserCoinOutRecord.setWalletAccount(userSylAccount);
        jydpUserCoinOutRecord.setCurrencyName(currencyName);
        jydpUserCoinOutRecord.setCurrencyNumber(number);
        jydpUserCoinOutRecord.setRemark(remark);
        jydpUserCoinOutRecord.setAddTime(curTime);

        if (excuteSuccess) {    //添加用户币种转出记录
            if (jydpCoinConfig.getFreeCurrencyNumber() <= number) {
                jydpUserCoinOutRecord.setHandleStatus(1);
                jydpUserCoinOutRecord.setOutStatus(1);

                excuteSuccess = jydpUserCoinOutRecordDao.inesertJydpUserCoinOutRecord(jydpUserCoinOutRecord);
            } else {
                jydpUserCoinOutRecord.setHandleStatus(2);
                jydpUserCoinOutRecord.setHandleTime(curTime);
                jydpUserCoinOutRecord.setOutStatus(2);

                excuteSuccess = jydpUserCoinOutRecordDao.inesertJydpUserCoinOutRecord(jydpUserCoinOutRecord);
            }
        }

        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return excuteSuccess;
    }

    /**
     * 根据条件查询币种转出记录数
     * @param coinRecordNo 记录号，没有填null
     * @param userAccount 用户账号，没有填null
     * @param walletAccount 转入账号，没有填null
     * @param currencyName 币种名称，没有填null
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录数；查询失败：返回0
     */
    public int countJydpUserCoinOutRecordForBack(String coinRecordNo, String userAccount, String walletAccount, String currencyName, int handleStatus,
                                   Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime){
        return jydpUserCoinOutRecordDao.countJydpUserCoinOutRecordForBack(coinRecordNo, userAccount, walletAccount, currencyName, handleStatus,
                                                                startAddTime, endAddTime, startFinishTime, endFinishTime);
    }

    /**
     * 根据条件查询币种转出记录
     * @param coinRecordNo 记录号，没有填null
     * @param userAccount 用户账号，没有填null
     * @param walletAccount 转入账号，没有填null
     * @param currencyName 币种名称，没有填null
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录集合；查询失败：返回null
     */
    public List<JydpUserCoinOutRecordDO> listJydpUserCoinOutRecord(String coinRecordNo, String userAccount, String walletAccount, String currencyName, int handleStatus,
                                                            Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime,
                                                            int pageNumber, int pageSize){
        return jydpUserCoinOutRecordDao.listJydpUserCoinOutRecord(coinRecordNo, userAccount, walletAccount, currencyName, handleStatus, startAddTime, endAddTime,
                                                            startFinishTime, endFinishTime, pageNumber, pageSize);

    }

    /**
     * 批量审核通过用户用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @return 操作成功：true；查询失败：false
     */
    public boolean updateHandleStatus(List<String> coinRecordNoList, String remark, Timestamp handleTime){
        return jydpUserCoinOutRecordDao.updateHandleStatus(coinRecordNoList, remark, handleTime);
    }

    /**
     * 批量审核拒绝用户用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @return 操作成功：true；查询失败：false
     */
    @Transactional
    public boolean updateRefuseHandleStatus(List<String> coinRecordNoList, String remarks, Timestamp handleTime){
        boolean executeSuccess = jydpUserCoinOutRecordDao.updateRefuseHandleStatus(coinRecordNoList, remarks, handleTime);
        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        List<UserBalanceDO> userBalanceList = new ArrayList<>();
        List<UserCurrencyNumDO> userCurrencyNumList = new ArrayList<>();
        Map<String,UserCurrencyNumDO> userCurrencyNumMap = new HashMap<String,UserCurrencyNumDO>();

        if(executeSuccess){
            jydpUserCoinOutRecordList = jydpUserCoinOutRecordDao.listJydpUserCoinOutRecordByCoinRecordNo(coinRecordNoList);
            if(jydpUserCoinOutRecordList == null){
                executeSuccess = false;
            }
        }

        if(executeSuccess){
            for (JydpUserCoinOutRecordDO jydpUserCoinOutRecordDO : jydpUserCoinOutRecordList){
                UserBalanceDO userBalanceDO = new UserBalanceDO();
                UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();

                Timestamp curTime = DateUtil.getCurrentTime();
                String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                                   NumberUtil.createNumberStr(10);
                int id = jydpUserCoinOutRecordDO.getUserId();
                String fromType = "币种提现";
                int currencyId = jydpUserCoinOutRecordDO.getCurrencyId();
                String currencyName = jydpUserCoinOutRecordDO.getCurrencyName();
                double balanceNumber = jydpUserCoinOutRecordDO.getCurrencyNumber();
                double frozenNumber= 0;
                String remark = "币种提现返还扣除币数量" + balanceNumber;

                userBalanceDO.setOrderNo(orderNo);
                userBalanceDO.setUserId(id);
                userBalanceDO.setFromType(fromType);
                userBalanceDO.setCurrencyId(currencyId);
                userBalanceDO.setCurrencyName(currencyName);
                userBalanceDO.setBalanceNumber(balanceNumber);
                userBalanceDO.setFrozenNumber(frozenNumber);
                userBalanceDO.setRemark(remark);
                userBalanceDO.setAddTime(curTime);

                userBalanceList.add(userBalanceDO);

                userCurrencyNumDO.setUserId(id);
                userCurrencyNumDO.setCurrencyId(currencyId);
                userCurrencyNumDO.setCurrencyNumber(balanceNumber);

                String key = id + "," + currencyId;

                if(userCurrencyNumMap.containsKey(key)){
                    double currencyNumber = userCurrencyNumMap.get(key).getCurrencyNumber();
                    currencyNumber = currencyNumber + balanceNumber;
                    userCurrencyNumMap.get(key).setCurrencyNumber(currencyNumber);
                }
                userCurrencyNumMap.put(key, userCurrencyNumDO);

            }

            userCurrencyNumList = (List<UserCurrencyNumDO>)userCurrencyNumMap.values();
            executeSuccess = userBalanceService.insertUserBalanceList(userBalanceList);

        }

        if(executeSuccess){
            for (int i= 0;i <userCurrencyNumList.size(); i++){
                UserCurrencyNumDO userCurrencyNumDO = userCurrencyNumList.get(i);
                executeSuccess = userCurrencyNumService.increaseCurrencyNumber(userCurrencyNumDO.getUserId(), userCurrencyNumDO.getCurrencyId(),
                        userCurrencyNumDO.getCurrencyNumber());
                if(!executeSuccess){
                    break;
                }
            }
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;

    }

    /**
     * 根据记录号查询记录
     * @param coinRecordNo 转出记录流水号
     * @return 查询成功：返回记录信息；查询失败：返回null
     */
    public JydpUserCoinOutRecordDO getJydpUserCoinOutRecordByRecordNo(String coinRecordNo){
        return jydpUserCoinOutRecordDao.getJydpUserCoinOutRecordByRecordNo(coinRecordNo);
    }
}
