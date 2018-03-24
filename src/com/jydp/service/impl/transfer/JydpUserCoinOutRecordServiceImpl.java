package com.jydp.service.impl.transfer;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.service.IJydpUserCoinOutRecordService;
import com.jydp.service.IUserBalanceService;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /** 用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

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
     * 查询用户币种转出记录总数
     * @param userAccount 用户账号
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    @Override
    public int countJydpUserCoinOutRecord(String userAccount) {
        return jydpUserCoinOutRecordDao.countJydpUserCoinOutRecord(userAccount);
    }

    /**
     * 撤销用户币种转出记录
     * @param userId 用户Id
     * @param coinRecordNo 转出记录流水号
     * @return 操作成功：返回true；操作失败：返回false；
     */
    @Override
    @Transactional
    public boolean withdrawUserCoinOutRecord(int userId,String coinRecordNo) {

        JydpUserCoinOutRecordDO jydpUserCoinOutRecord = jydpUserCoinOutRecordDao.getJydpUserCoinOutRecordByRecordNo(coinRecordNo);

        if (jydpUserCoinOutRecord == null || jydpUserCoinOutRecord.getHandleStatus() != 1) {
            return false;
        }

        //业务执行状态
        boolean excuteSuccess = true;
        Timestamp curTime = DateUtil.getCurrentTime();
        String currencyName = jydpUserCoinOutRecord.getCurrencyName();
        double tradePriceSum = jydpUserCoinOutRecord.getCurrencyNumber();
        if (excuteSuccess) {
            //撤销用户币种提现申请
            excuteSuccess = jydpUserCoinOutRecordDao.withdrawUserCoinOutRecord(coinRecordNo);
        }

        if (excuteSuccess) {
            String orderNo = SystemCommonConfig.USER_BALANCE +
                    DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                    NumberUtil.createNumberStr(10);
            String remark = "撤销" + currencyName + "提币申请，返还冻结币";

            UserBalanceDO userBalance = new UserBalanceDO();
            userBalance.setOrderNo(orderNo);
            userBalance.setUserId(userId);
            userBalance.setCurrencyId(jydpUserCoinOutRecord.getCurrencyId());
            userBalance.setCurrencyName(currencyName);
            userBalance.setFromType(UserBalanceConfig.BUY_ENTRUST);
            userBalance.setBalanceNumber(tradePriceSum);
            userBalance.setFrozenNumber(-tradePriceSum);
            userBalance.setRemark(remark);
            userBalance.setAddTime(curTime);
        }
//        jydpUserCoinOutRecordDao
        return false;
    }
}
