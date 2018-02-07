package com.jydp.service;


import com.jydp.entity.DO.back.BackerDO;

import java.util.List;

/**
 * 后台管理员
 * @author sy
 *
 */
public interface IBackerService {

    /**
     * 验证该账号是否存在
     * @param backerAccount 后台管理员帐号
     * @return 已存在：返回true，不存在：返回false
     */
    boolean validateBackerExist(String backerAccount);
    
    /**
     * 验证账号密码是否正确
     * @param backerAccount 后台管理员帐号
     * @param password 密码
     * @return 操作成功：返回该实体，操作失败：null
     */
    BackerDO validateBackerLogin(String backerAccount, String password);
    
    /**
     * 新增后台管理员账号
     * @param backer 后台管理员账号
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean insertBacker(BackerDO backer);
   
    /**
     * 根据Id查询后台管理员
     * @param backerId 后台管理员Id
     * @return 查询成功：返回后台管理员账号信息，查询失败：返回null
     */
    BackerDO getBackerById(int backerId);
    
    /**
     * 根据账号查询后台管理员
     * @param backerAccount 后台管理员账号
     * @return 查询成功：返回后台管理员账号信息，查询失败：返回null
     */
    BackerDO getBackerByAccount(String backerAccount);
    
    /**
     * 分页查询后台管理员账号
     * @param pageNumber 查询的页码
     * @param pageSize 每页的信息数量
     * @return 操作成功：返回后台管理员账号信息，操作失败：返回null
     */
    List<BackerDO> listBackerByPage(int pageNumber, int pageSize);
   
    /**
     * 查询角色下的管理员数量（主要用于删除角色时判断）
     * @param roleId 角色Id
     * @return 操作成功：返回总数，失败：返回0
     */
    int getBackerNumberByRoleId(int roleId);
    
    /**
     * 查询后台管理员账号总数
     * @return 操作成功：查询到的总数，操作失败：返回null
     */
    int countBacker();
    
    /**
     * 查询角色关联的后台管理员数量
     * @param roleId 角色Id
     * @return 查询成功：查询到的总数，查询失败：返回null
     */
    int countBackerByRoleId(int roleId);
    
    /**
     * 根据Id修改后台管理员角色
     * @param backerId 后台管理员账号Id
     * @param roleId 修改后的角色Id
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateBackerRole(int backerId, int roleId);
    
    /**
     * 修改密码
     * @param backerId 后台管理员Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updatePassword(int backerId, String oldPassword, String newPassword);
    
    /**
     * 重置密码
     * @param backerId 后台管理员账号Id
     * @param newPassword 新密码
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean resetPassword(int backerId, String newPassword);
    
    /**
     * 修改帐号状态
     * @param backerId 后台管理员账号Id
     * @param accountStatus 帐号状态，1：启用，2：禁用，-1：删除
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateAccountStatus(int backerId, int accountStatus);

}
