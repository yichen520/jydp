package com.iqmkj.config;

import com.iqmkj.utils.DateUtil;
import com.jydp.entity.DO.system.SystemSwitchRecordDO;
import com.jydp.service.ISystemSwitchRecordService;
import config.CheckUserAmountConfig;
import config.SystemSwitchConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统初始化操作
 * @author whx
 */
public class SystemCommonInit {

    /** 系统开关记录 */
    @Autowired
    private ISystemSwitchRecordService systemSwitchRecordService;

    /** 执行初始化 */
    public void executeInit() {
        systemSwitchCheckAmountInit();
    }

    /** 初始化系统开关---核对用户美金账户，数字货币账户 */
    public void systemSwitchCheckAmountInit() {
        //不存在则新增开关
        int switchStatus = systemSwitchRecordService.getNewestSendSwitchStatus(SystemSwitchConfig.CHECK_AMOUNT_CODE);
        if (switchStatus != 0) {
            return;
        }

        SystemSwitchRecordDO systemSwitchRecordDO = new SystemSwitchRecordDO();
        systemSwitchRecordDO.setSwitchCode(SystemSwitchConfig.CHECK_AMOUNT_CODE);
        systemSwitchRecordDO.setSwitchName(SystemSwitchConfig.switchCodeMap.get(SystemSwitchConfig.CHECK_AMOUNT_CODE));
        systemSwitchRecordDO.setSwitchStatus(1);
        systemSwitchRecordDO.setBackerId(CheckUserAmountConfig.SYSTEM_ID);
        systemSwitchRecordDO.setBackerAccount(CheckUserAmountConfig.SYSTEM_ACCOUNT);
        systemSwitchRecordDO.setIpAddress(CheckUserAmountConfig.IP_ADDRESS);
        systemSwitchRecordDO.setAddTime(DateUtil.getCurrentTime());
        //新增系统开关记录
        systemSwitchRecordService.insertSystemSwitchRecord(systemSwitchRecordDO);
    }

}
