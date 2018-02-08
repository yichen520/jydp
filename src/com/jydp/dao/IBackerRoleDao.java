package com.jydp.dao;

import com.jydp.entity.DO.back.BackerRoleDO;
import com.jydp.entity.DTO.BackerRoleDTO;

import java.util.List;

/**
 * 账号角色
 * @author sy
 */
public interface IBackerRoleDao {

	/**
	 * 新增账号角色
	 * @param backerRole 待新增的账号角色信息
	 * @return 操作成功:返回账号角色id, 操作失败:返回null
	 */
	int insertRole(BackerRoleDO backerRole);
	
	/**
	 * 查询账号角色,根据角色Id
	 * @param roleId 角色Id
	 * @return 操作成功:返回账号角色信息, 操作失败:返回null
	 */
	BackerRoleDO getRoleById(int roleId);
	
	/**
	 * 查询账号角色列表
	 * @return 操作成功:返回账号角色列表, 操作失败:返回null
	 */
	List<BackerRoleDO> listRole();

	/**
	 * 查询账号角色权限列表
	 * @param backerId 后台管理员Id
	 * @return 操作成功：返回账号角色权限列表，操作失败：返回null
	 */
	List<BackerRoleDTO> listRolePower(int backerId);
	
	/**
	 * 查询账号角色权限
	 * @param roleId 角色Id
	 * @return 操作成功：返回账号角色权限，操作失败：返回null
	 */
	BackerRoleDTO getRolePower(int roleId);
	
	/**
	 * 修改账号角色名称
	 * @param roleId 角色Id
	 * @param roleName 新角色名称
	 * @return 操作成功:返回true, 操作失败:返回false
	 */
	boolean updateRoleNameById(int roleId, String roleName);
	
	/**
	 * 删除账号角色
	 * @param roleId 角色Id
	 * @return 操作成功:返回true, 操作失败:返回false
	 */
	boolean deleteRoleById(int roleId);
}
