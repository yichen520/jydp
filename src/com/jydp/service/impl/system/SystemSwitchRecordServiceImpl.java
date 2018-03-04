package com.jydp.service.impl.system;

import com.jydp.dao.ISystemSwitchRecordDao;
import com.jydp.entity.DO.system.SystemSwitchRecordDO;
import com.jydp.entity.DTO.SystemSwitchRecordDTO;
import com.jydp.service.ISystemSwitchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统开关记录
 * @author whx
 *
 */
@Service("systemSwitchRecordService")
public class SystemSwitchRecordServiceImpl implements ISystemSwitchRecordService {

    /** 系统开关记录 */
    @Autowired
    private ISystemSwitchRecordDao systemSwitchRecordDao;

    /**
     * 新增系统开关记录
     * @param systemSwitchRecordDO 待新增的 系统开关记录
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertSystemSwitchRecord(SystemSwitchRecordDO systemSwitchRecordDO) {
        return systemSwitchRecordDao.insertSystemSwitchRecord(systemSwitchRecordDO);
    }

    /**
     * 查询系统开关记录
     * @param id 记录Id
     * @return 查询成功：返回系统开关记录，查询失败或无数据，返回null
     */
    public SystemSwitchRecordDO getSystemSwitchRecordById(int id) {
        return systemSwitchRecordDao.getSystemSwitchRecordById(id);
    }

    /**
     * 查询最新的系统开关状态
     * @param switchCode 开关编码，详见《系统开关编码表》
     * @return 查询成功：返回系统开关状态，1：开启，2：关闭，查询失败或无数据，返回0
     */
    public int getNewestSendSwitchStatus(int switchCode) {
        return systemSwitchRecordDao.getNewestSendSwitchStatus(switchCode);
    }

    /**
     * 查询最新的系统开关记录信息
     * @param switchCode 开关编码，详见《系统开关编码表》
     * @return 查询成功：返回开关记录，查询失败或无数据，返回null
     */
    public SystemSwitchRecordDTO getNewestSystemSwitchRecord(int switchCode) {
        return systemSwitchRecordDao.getNewestSystemSwitchRecord(switchCode);
    }

}