package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IBackerDao;
import com.jydp.entity.DO.back.BackerDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理员
 * @author sy
 *
 */
@Repository
public class BackerDaoImpl implements IBackerDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * 验证该账号是否存在
     * @param backerAccount 后台管理员帐号
     * @return 已存在：返回true，不存在：返回false
     */
    public boolean validateBackerExist(String backerAccount) {
        int id = 0;
        try {
            id = sqlSessionTemplate.selectOne("Backer_validateBackerExist",backerAccount);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(id > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 验证账号密码是否正确
     * @param backerAccount 后台管理员帐号
     * @param password 密码
     * @return 操作成功：返回该实体，操作失败：null
     */
    public BackerDO validateBackerLogin(String backerAccount, String password) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("backerAccount", backerAccount);
        map.put("password", password);
        
        BackerDO backer = null;
        try {
            backer = sqlSessionTemplate.selectOne("Backer_validateBackerLogin", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return backer;
    }

    /**
     * 新增后台管理员账号
     * @param backer 后台管理员账号
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertBacker(BackerDO backer) {
        int result = 0;
        
        try {
            result = sqlSessionTemplate.insert("Backer_insertBacker", backer);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据Id查询后台管理员
     * @param backerId 后台管理员Id
     * @return 查询成功：返回后台管理员账号信息，查询失败：返回null
     */
    public BackerDO getBackerById(int backerId) {
        BackerDO backer = null;
        
        try {
            backer = sqlSessionTemplate.selectOne("Backer_getBackerById", backerId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return backer;
    }

    /**
     * 根据账号查询后台管理员
     * @param backerAccount 后台管理员账号
     * @return 查询成功：返回后台管理员账号信息，查询失败：返回null
     */
    public BackerDO getBackerByAccount(String backerAccount) {
        BackerDO backer = null;
        
        try {
            backer = sqlSessionTemplate.selectOne("Backer_getBackerByAccount", backerAccount);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return backer;
    }

    /**
     * 分页查询后台管理员账号
     * @param pageNumber 查询的页码
     * @param pageSize 每页的信息数量
     * @return 操作成功：返回后台管理员账号信息，操作失败：返回null
     */
    public List<BackerDO> listBackerByPage(int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startNumber", pageSize*pageNumber);
        map.put("pageSize", pageSize);
        
        List<BackerDO> resultList = null;
        
        try {
            resultList = sqlSessionTemplate.selectList("Backer_listBackerByPage", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return resultList;
    }
    
    /**
     * 查询角色下的管理员数量（主要用于删除角色时判断）
     * @param roleId 角色Id
     * @return 操作成功：返回总数，失败：返回0
     */
    public int getBackerNumberByRoleId(int roleId) {
        int resultNumber = 0;
        try {
            resultNumber = sqlSessionTemplate.selectOne("Backer_getBackerNumberByRoleId", roleId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return resultNumber;
    }

    /**
     * 查询后台管理员账号总数
     * @return 操作成功：查询到的总数，操作失败：返回null
     */
    public int countBacker() {
        int resultNumber = 0;
        try {
            resultNumber = sqlSessionTemplate.selectOne("Backer_countBacker");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return resultNumber;
    }

    /**
     * 查询角色关联的后台管理员数量
     * @param roleId 角色Id
     * @return 查询成功：查询到的总数，查询失败：返回null
     */
    public int countBackerByRoleId(int roleId) {
        int resultNumber = 0;
        try {
            resultNumber = sqlSessionTemplate.selectOne("Backer_countBackerByRoleId", roleId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return resultNumber;
    }
    
    /**
     * 根据Id修改后台管理员角色
     * @param backerId 后台管理员账号Id
     * @param roleId 修改后的角色Id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateBackerRole(int backerId, int roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("backerId", backerId);
        map.put("roleId", roleId);
        
        int result = 0;
        try {
            result = sqlSessionTemplate.update("Backer_updateBackerRole", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 修改密码
     * @param backerId 后台管理员账号Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePassword(int backerId, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("backerId", backerId);
        map.put("oldPassword", oldPassword);
        map.put("newPassword", newPassword);
        
        int result = 0;
        try {
            result = sqlSessionTemplate.update("Backer_updatePassword", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 重置密码
     * @param backerId 后台管理员账号Id
     * @param newPassword 新密码
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean resetPassword(int backerId, String newPassword) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("backerId", backerId);
        map.put("newPassword", newPassword);
        
        int result = 0;
        try {
            result = sqlSessionTemplate.update("Backer_resetPassword", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 修改帐号状态
     * @param backerId 后台管理员账号Id
     * @param accountStatus 帐号状态，1：启用，2：禁用，-1：删除
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateAccountStatus(int backerId, int accountStatus) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("backerId", backerId);
        map.put("accountStatus", accountStatus);
        
        int result = 0;
        try {
            result = sqlSessionTemplate.update("Backer_updateAccountStatus", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

}
