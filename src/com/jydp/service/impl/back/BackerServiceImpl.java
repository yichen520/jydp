package com.jydp.service.impl.back;


import com.jydp.dao.IBackerDao;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.service.IBackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * 后台管理员
 * @author sy
 *
 */
@Service("backerService")
public class BackerServiceImpl implements IBackerService {
    
    /**后台管理员*/
    @Autowired
    private IBackerDao backerDao;
    
    /**
     * 验证该账号是否存在
     * @param backerAccount 后台管理员帐号
     * @return 已存在：返回true，不存在：返回false
     */
    public boolean validateBackerExist(String backerAccount) {
        return backerDao.validateBackerExist(backerAccount);
    }

    /**
     * 验证账号密码是否正确
     * @param backerAccount 后台管理员帐号
     * @param password 密码
     * @return 操作成功：返回该实体，操作失败：null
     */
    public BackerDO validateBackerLogin(String backerAccount, String password) {
        return backerDao.validateBackerLogin(backerAccount, password);
    }

    /**
     * 新增后台管理员账号
     * @param backer 后台管理员账号
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertBacker(BackerDO backer) {
        return backerDao.insertBacker(backer);
    }

    /**
     * 根据Id查询后台管理员
     * @param backerId 后台管理员Id
     * @return 查询成功：返回后台管理员账号信息，查询失败：返回null
     */
    public BackerDO getBackerById(int backerId) {
        return backerDao.getBackerById(backerId);
    }

    /**
     * 根据账号查询后台管理员
     * @param backerAccount 后台管理员账号
     * @return 查询成功：返回后台管理员账号信息，查询失败：返回null
     */
    public BackerDO getBackerByAccount(String backerAccount) {
        return backerDao.getBackerByAccount(backerAccount);
    }

    /**
     * 分页查询后台管理员账号
     * @param pageNumber 查询的页码
     * @param pageSize 每页的信息数量
     * @return 操作成功：返回后台管理员账号信息，操作失败：返回null
     */
    public List<BackerDO> listBackerByPage(int pageNumber, int pageSize) {
        return backerDao.listBackerByPage(pageNumber, pageSize);
    }

    /**
     * 查询角色下的管理员数量（主要用于删除角色时判断）
     * @param roleId 角色Id
     * @return 操作成功：返回总数，失败：返回0
     */
    public int getBackerNumberByRoleId(int roleId) {
        return backerDao.getBackerNumberByRoleId(roleId);
    }

    /**
     * 查询后台管理员账号总数
     * @return 操作成功：查询到的总数，操作失败：返回null
     */
    public int countBacker() {
        return backerDao.countBacker();
    }

    /**
     * 查询角色关联的后台管理员数量
     * @param roleId 角色Id
     * @return 查询成功：查询到的总数，查询失败：返回null
     */
    public int countBackerByRoleId(int roleId) {
        return backerDao.countBackerByRoleId(roleId);
    }
    
    /**
     * 根据Id修改后台管理员角色
     * @param backerId 后台管理员账号Id
     * @param roleId 修改后的角色Id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateBackerRole(int backerId, int roleId) {
        return backerDao.updateBackerRole(backerId, roleId);
    }
    
    /**
     * 修改密码
     * @param backerId 后台管理员账号Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePassword(int backerId, String oldPassword, String newPassword) {
        return backerDao.updatePassword(backerId, oldPassword, newPassword);
    }
    
    /**
     * 重置密码
     * @param backerId 后台管理员账号Id
     * @param newPassword 新密码
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean resetPassword(int backerId, String newPassword) {
        return backerDao.resetPassword(backerId, newPassword);
    }
    
    /**
     * 修改帐号状态
     * @param backerId 后台管理员账号Id
     * @param accountStatus 帐号状态，1：启用，2：禁用，-1：删除
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateAccountStatus(int backerId, int accountStatus) {
        return backerDao.updateAccountStatus(backerId, accountStatus);
    }

}