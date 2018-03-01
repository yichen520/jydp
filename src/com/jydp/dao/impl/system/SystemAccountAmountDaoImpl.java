package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemAccountAmountDao;
import com.jydp.entity.DO.system.SystemAccountAmountDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统账户金额
 * @author hz
 *
 */
@Repository
public class SystemAccountAmountDaoImpl implements ISystemAccountAmountDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 系统账户金额
     * @param systemAccountAmountDO 待新增的 系统账户金额
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSystemAccountAmount(SystemAccountAmountDO systemAccountAmountDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SystemAccountAmount_insertSystemAccountAmount", systemAccountAmountDO);
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
     * 获取所有系统账户金额
     * @return 查询成功：返回 系统账户金额list，查询失败，返回null
     */
    public List<SystemAccountAmountDO> getSystemAccountAmountAllForBack() {
        List<SystemAccountAmountDO> systemAccountAmountList = null;

        try {
            systemAccountAmountList = sqlSessionTemplate.selectList("SystemAccountAmount_getSystemAccountAmountAllForBack");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemAccountAmountList;
    }

    /**
     * 根据系统账户编码拿到 系统账户金额
     * @param accountCode 系统账户编码
     * @return 查询成功：返回 系统账户金额，查询失败，返回null
     */
    public SystemAccountAmountDO getSystemAccountAmountById(int accountCode) {
        SystemAccountAmountDO systemAccountAmountDO = null;

        try {
            systemAccountAmountDO = sqlSessionTemplate.selectOne("SystemAccountAmount_getSystemAccountAmountById", accountCode);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemAccountAmountDO;
    }

    /**
     * 增加系统账户金额
     * @param accountCode 系统账户编码
     * @param amount 待增加金额
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean addSystemAccountAmount(int accountCode, double amount) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountCode", accountCode);
        map.put("amount", amount);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("SystemAccountAmount_addSystemAccountAmount", map);
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
     * 修改 系统账户金额
     * @param systemAccountAmountDO 系统账户金额实体
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemAccountAmount(SystemAccountAmountDO systemAccountAmountDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemAccountAmount_updateSystemAccountAmount", systemAccountAmountDO);
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
     * 删除 系统账户金额
     * @param accountCode 系统账户编码
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemAccountAmount(int accountCode) {
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("SystemAccountAmount_deleteSystemAccountAmount", accountCode);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
