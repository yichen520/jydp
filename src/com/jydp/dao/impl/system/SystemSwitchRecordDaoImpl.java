package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemSwitchRecordDao;
import com.jydp.entity.DO.system.SystemSwitchRecordDO;
import com.jydp.entity.DTO.SystemSwitchRecordDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 系统自动发放开关记录
 * @author whx
 *
 */
@Repository
public class SystemSwitchRecordDaoImpl implements ISystemSwitchRecordDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增系统开关记录
     * @param systemSwitchRecordDO 待新增的 系统开关记录
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertSystemSwitchRecord(SystemSwitchRecordDO systemSwitchRecordDO) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("SystemSwitchRecord_insertSystemSwitchRecord", systemSwitchRecordDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询系统开关记录
     * @param id 记录Id
     * @return 查询成功：返回系统开关记录，查询失败或无数据，返回null
     */
    public SystemSwitchRecordDO getSystemSwitchRecordById(int id) {
        SystemSwitchRecordDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("SystemSwitchRecord_getSystemSwitchRecordById", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询最新的系统开关状态
     * @param switchCode 开关编码，详见《系统开关编码表》
     * @return 查询成功：返回系统开关状态，1：开启，2：关闭，查询失败或无数据，返回0
     */
    public int getNewestSendSwitchStatus(int switchCode) {
        Integer result = null;
        try {
            result = sqlSessionTemplate.selectOne("SystemSwitchRecord_getNewestSendSwitchStatus", switchCode);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result == null) {
            return 0;
        } else {
            return result;
        }
    }

    /**
     * 查询最新的系统开关记录信息
     * @param switchCode 开关编码，详见《系统开关编码表》
     * @return 查询成功：返回开关记录，查询失败或无数据，返回null
     */
    public SystemSwitchRecordDTO getNewestSystemSwitchRecord(int switchCode) {
        SystemSwitchRecordDTO result = null;
        try {
            result = sqlSessionTemplate.selectOne("SystemSwitchRecord_getNewestSystemSwitchRecord", switchCode);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

}