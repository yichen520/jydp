package com.jydp.dao;

import com.jydp.entity.DO.back.BackerRolePowerMapDO;

import java.util.List;

/**
 * 角色权限映射
 * @author sy
 *
 */
public interface IBackerRolePowerMapDao {

	/**
	 * 查询角色权限映射
	 * @param powerId 权限Id
	 * @return 操作成功：角色权限映射信息，操作失败：返回null
	 */
	BackerRolePowerMapDO getRolePowerMap(int powerId);
	
	/**
	 * 查询角色权限映射全部信息
	 * @return 操作成功：返回角色权限映射全部信息，操作失败：返回null
	 */
	List<BackerRolePowerMapDO> listRolePowerAll();

}
