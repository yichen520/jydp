package com.jydp.service;


import com.jydp.entity.DO.back.BackerRolePowerDO;

/**
 * 账号角色权限
 * @author sy
 */
public interface IBackerRolePowerService {

	/**
	 * 新增角色权限
	 * @param backerRolePower 账号角色权限
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	boolean insertRolePower(BackerRolePowerDO backerRolePower);
	
	/**
	 * 查询角色权限
	 * @param roleId 角色Id
	 * @return 查询成功：返回角色权限信息，查询失败：返回null
	 */
	BackerRolePowerDO getRolePower(int roleId);
    
	/**
	 * 修改角色权限信息
	 * @param roleId 角色Id
	 * @param powerJson 权限信息，Json格式字符串
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	boolean updateRolePower(int roleId, String powerJson);
	
    /**
     * 删除角色的权限（删除角色下的全部权限）
     * @param roleId 角色Id
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean deleteRolePowerByRoleId(int roleId);
    
}
