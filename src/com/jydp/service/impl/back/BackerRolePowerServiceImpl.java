package com.jydp.service.impl.back;


import com.jydp.dao.IBackerRolePowerDao;
import com.jydp.entity.DO.back.BackerRolePowerDO;
import com.jydp.service.IBackerRolePowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账号角色权限
 * @author sy
 */
@Service("backerRolePowerService")
public class BackerRolePowerServiceImpl implements IBackerRolePowerService {

	/** 账号角色权限  */
	@Autowired
	private IBackerRolePowerDao backerRolePowerDao;

	/**
	 * 新增角色权限
	 * @param backerRolePower 账号角色权限
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean insertRolePower(BackerRolePowerDO backerRolePower) {
		return backerRolePowerDao.insertRolePower(backerRolePower);
	}

	/**
	 * 查询角色权限
	 * @param roleId 角色Id
	 * @return 查询成功：返回角色权限信息，查询失败：返回null
	 */
	public BackerRolePowerDO getRolePower(int roleId) {
		return backerRolePowerDao.getRolePower(roleId);
	}
	
	/**
	 * 修改角色权限信息
	 * @param roleId 角色Id
	 * @param powerJson 权限信息，Json格式字符串
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean updateRolePower(int roleId, String powerJson) {
		return backerRolePowerDao.updateRolePower(roleId, powerJson);
	}
	
	/**
     * 删除角色的权限
     * @param roleId 角色Id
     * @return 操作成功：返回true，操作失败：返回false
     */
	public boolean deleteRolePowerByRoleId(int roleId) {
		return backerRolePowerDao.deleteRolePowerByRoleId(roleId);
	}

}
