package com.jydp.service.impl.transfer;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.VO.UserCoinConfigVO;
import com.jydp.service.IJydpCoinConfigService;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.IUserBalanceService;
import com.jydp.service.IUserCurrencyNumService;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.List;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
@Service("jydpUserCoinOutRecordService")
public class JydpUserCoinOutRecordServiceImpl implements IJydpUserCoinOutRecordService {

    /**  JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordDao jydpUserCoinOutRecordDao;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /**  JYDP币种转出管理 */
    @Autowired
    private IJydpCoinConfigService jydpCoinConfigService;

    /**
     * 根据用户账号查询用户币种转出记录
     * @param userAccount 用户账号
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    @Override
    public List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(String userAccount) {
        return jydpUserCoinOutRecordDao.getJydpUserCoinOutRecordlist(userAccount);
    }

    /**
     * 用户币种提现记录(提币数量小于免审数量)
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

        String orderNo = SystemCommonConfig.COIN_OUT +
                DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(7);
        String remark = currencyName + ": 币种提现" + number;

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

        if (jydpCoinConfig.getFreeCurrencyNumber() <= number) {
            jydpUserCoinOutRecord.setHandleStatus(1);
            jydpUserCoinOutRecord.setOutStatus(1);
            return jydpUserCoinOutRecordDao.inesertJydpUserCoinOutRecord(jydpUserCoinOutRecord);
        }

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

            excuteSuccess = userBalanceService.insertUserBalance(userBalance);
        }

        if (excuteSuccess) {
            jydpUserCoinOutRecord.setHandleStatus(2);
            jydpUserCoinOutRecord.setHandleTime(curTime);
            jydpUserCoinOutRecord.setOutStatus(2);

            excuteSuccess = jydpUserCoinOutRecordDao.inesertJydpUserCoinOutRecord(jydpUserCoinOutRecord);
        }

        if(!excuteSuccess){
            //数据回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return excuteSuccess;
    }
}
