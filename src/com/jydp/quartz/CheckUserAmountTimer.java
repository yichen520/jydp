package com.jydp.quartz;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.LogUtil;
import com.jydp.entity.DO.system.SystemSwitchRecordDO;
import com.jydp.entity.DTO.UserAmountCheckDTO;
import com.jydp.other.SendMessage;
import com.jydp.service.ISystemSwitchRecordService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import config.CheckUserAmountConfig;
import config.SystemSwitchConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 核对用户美金账户，数字货币账户
 * @author whx
 */
@Component
public class CheckUserAmountTimer {

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 系统开关记录 */
    @Autowired
    private ISystemSwitchRecordService systemSwitchRecordService;

    /** 执行对账操作 (每分钟核对一次)*/
    @Scheduled(cron="0 0/1 *  * * ? ")
    public void executeCheck() {
        //系统开关1：开启，2：关闭
        int switchCode = systemSwitchRecordService.getNewestSendSwitchStatus(SystemSwitchConfig.CHECK_AMOUNT_CODE);
        if (switchCode != 1) {
            return;
        }

        boolean usdBoolean = checkUSD();//核对用户美金账户
        boolean coinBoolean = checkCoin();//核对用户数字货币账户
        if (!usdBoolean && !coinBoolean) {
            return;
        }

        SystemSwitchRecordDO systemSwitchRecordDO = new SystemSwitchRecordDO();
        systemSwitchRecordDO.setSwitchCode(SystemSwitchConfig.CHECK_AMOUNT_CODE);
        systemSwitchRecordDO.setSwitchName(SystemSwitchConfig.switchCodeMap.get(SystemSwitchConfig.CHECK_AMOUNT_CODE));
        systemSwitchRecordDO.setSwitchStatus(2);//关闭开关，处理用户账户数据后需手动开启
        systemSwitchRecordDO.setBackerId(CheckUserAmountConfig.SYSTEM_ID);
        systemSwitchRecordDO.setBackerAccount(CheckUserAmountConfig.SYSTEM_ACCOUNT);
        systemSwitchRecordDO.setIpAddress(CheckUserAmountConfig.IP_ADDRESS);
        systemSwitchRecordDO.setAddTime(DateUtil.getCurrentTime());
        //新增系统开关记录
        systemSwitchRecordService.insertSystemSwitchRecord(systemSwitchRecordDO);
    }

    /** 核对用户美金账户 */
    public boolean checkUSD() {
        //用户美金账户出错总数
        int beyondUserTotal = userService.countCheckUserAmountForTimer(CheckUserAmountConfig.USD_CURRENCYID,
                CheckUserAmountConfig.USD_BEYOND_MAX, CheckUserAmountConfig.USD_BEYOND_LOCK_MAX);
        if (beyondUserTotal <= 0) {
            return false;
        }
        SendMessage.send(CheckUserAmountConfig.NOTICE_PHONE_ONE, CheckUserAmountConfig.MESSAGE_USD);
        //测试代码 TODO
        List<UserAmountCheckDTO> userAmountCheckList = userService.listCheckUserAmountForTimer(CheckUserAmountConfig.USD_CURRENCYID,
                CheckUserAmountConfig.USD_BEYOND_MAX, CheckUserAmountConfig.USD_BEYOND_LOCK_MAX, 0, 100);
        if (CollectionUtils.isNotEmpty(userAmountCheckList)) {
            LogUtil.printInfoLog(userAmountCheckList.toString());
        }
        return true;
    }

    /** 核对用户数字货币账户 */
    public boolean checkCoin() {
        //用户数字货币出错总数
        int beyondUserTotal = userCurrencyNumService.countCheckUserAmountForTimer(CheckUserAmountConfig.COIN_BEYOND_MAX,
                CheckUserAmountConfig.COIN_BEYOND_LOCK_MAX);
        if (beyondUserTotal <= 0) {
            return false;
        }
        SendMessage.send(CheckUserAmountConfig.NOTICE_PHONE_ONE, CheckUserAmountConfig.MESSAGE_COIN);
        //测试代码 TODO
        List<UserAmountCheckDTO> userAmountCheckList = userCurrencyNumService.listCheckUserAmountForTimer(CheckUserAmountConfig.COIN_BEYOND_MAX,
                CheckUserAmountConfig.COIN_BEYOND_LOCK_MAX, 0, 100);
        if (CollectionUtils.isNotEmpty(userAmountCheckList)) {
            LogUtil.printInfoLog(userAmountCheckList.toString());
        }
        return true;
    }

}
