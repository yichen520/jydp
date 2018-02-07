package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IBackerRoleDao;
import com.jydp.entity.DO.back.BackerRoleDO;
import com.jydp.entity.DTO.BackerRoleDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号角色
 * @author sy
 */
@Repository
public class BackerRoleDaoImpl implements IBackerRoleDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 新增账号角色
	 * @param backerRole 待新增的账号角色信息
	 * @return 操作成功:返回账号角色信息, 操作失败:返回null
	 */
	public int insertRole(BackerRoleDO backerRole) {
		int result = 0;
		try {
			result = sqlSessionTemplate.insert("BackerRole_insertRole", backerRole);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		
		if (result > 0 && backerRole.getRoleId() > 0) {
			return backerRole.getRoleId();
		} else {
			return 0;
		}
	}

	/**
	 * 查询账号角色,根据角色Id
	 * @param roleId 角色Id
	 * @return 操作成功:返回账号角色信息, 操作失败:返回null
	 */
	public BackerRoleDO getRoleById(int roleId) {
		BackerRoleDO result = null;
		try {
			result = sqlSessionTemplate.selectOne("BackerRole_getRoleById", roleId);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		return result;
	}

	/**
	 * 查询账号角色列表
	 * @return 操作成功:返回账号角色列表, 操作失败:返回null
	 */
	public List<BackerRoleDO> listRole() {
		List<BackerRoleDO> resultList = null;
		try {
			resultList = sqlSessionTemplate.selectList("BackerRole_listRole");
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		return resultList;
	}

	/**
	 * 查询账号角色权限列表
	 * @param backerId 后台管理员Id
	 * @return 操作成功：返回账号角色权限列表，操作失败：返回null
	 */
	public List<BackerRoleDTO> listRolePower(int backerId) {
		List<BackerRoleDTO> resultList = null;
		try {
			resultList = sqlSessionTemplate.selectList("BackerRole_listRolePower", backerId);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		return resultList;
	}
	
	/**
	 * 查询账号角色权限
	 * @param roleId 角色Id
	 * @return 操作成功：返回账号角色权限，操作失败：返回null
	 */
	public BackerRoleDTO getRolePower(int roleId) {
		BackerRoleDTO result = null;
		try {
			result = sqlSessionTemplate.selectOne("BackerRole_getRolePower", roleId);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		return result;
	}
	
	/**
	 * 修改账号角色名称
	 * @param roleId 角色Id
	 * @param roleName 新角色名称
	 * @return 操作成功:返回true, 操作失败:返回false
	 */
	public boolean updateRoleNameById(int roleId, String roleName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("roleName", roleName);
		
		int result = 0;
		try {
			result = sqlSessionTemplate.update("BackerRole_updateRoleNameById", map);
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
	 * 删除账号角色
	 * @param roleId 角色Id
	 * @return 操作成功:返回true, 操作失败:返回false
	 */
	public boolean deleteRoleById(int roleId) {
		int result = 0;
		try {
			result = sqlSessionTemplate.delete("BackerRole_deleteRoleById", roleId);
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
