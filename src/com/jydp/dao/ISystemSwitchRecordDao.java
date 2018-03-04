package com.jydp.dao;

import com.jydp.entity.DO.system.SystemSwitchRecordDO;
import com.jydp.entity.DTO.SystemSwitchRecordDTO;

/**
 * 系统开关记录
 * @author whx
 *
 */
public interface ISystemSwitchRecordDao {

    /**
     * 新增系统开关记录
     * @param systemSwitchRecordDO 待新增的 系统开关记录
     * @return 新增成功：返回true，新增失败：返回false
     */
    boolean insertSystemSwitchRecord(SystemSwitchRecordDO systemSwitchRecordDO);

    /**
     * 查询系统开关记录
     * @param id 记录Id
     * @return 查询成功：返回系统开关记录，查询失败或无数据，返回null
     */
    SystemSwitchRecordDO getSystemSwitchRecordById(int id);

    /**
     * 查询最新的系统开关状态
     * @param switchCode 开关编码，详见《系统开关编码表》
     * @return 查询成功：返回系统开关状态，1：开启，2：关闭，查询失败或无数据，返回0
     */
    int getNewestSendSwitchStatus(int switchCode);

    /**
     * 查询最新的系统开关记录信息
     * @param switchCode 开关编码，详见《系统开关编码表》
     * @return 查询成功：返回开关记录，查询失败或无数据，返回null
     */
    SystemSwitchRecordDTO getNewestSystemSwitchRecord(int switchCode);
    
}
