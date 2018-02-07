package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IBackerRolePowerDao;
import com.jydp.entity.DO.back.BackerRolePowerDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 账户角色权限
 * @author sy
 */
@Repository
public class BackerRolePowerDaoImpl implements IBackerRolePowerDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 新增角色权限
	 * @param backerRolePower 账户角色权限
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean insertRolePower(BackerRolePowerDO backerRolePower) {
		int result = 0;
		try {
			result = sqlSessionTemplate.insert("BackerRolePower_insertRolePower", backerRolePower);
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
	 * 查询角色权限
	 * @param roleId 角色Id
	 * @return 查询成功：返回角色权限信息，查询失败：返回null
	 */
	public BackerRolePowerDO getRolePower(int roleId) {
		BackerRolePowerDO backerRolePower = null;
		try {
			backerRolePower = sqlSessionTemplate.selectOne("BackerRolePower_getRolePower", roleId);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		return backerRolePower;
	}
	
	/**
	 * 修改角色权限信息
	 * @param roleId 角色Id
	 * @param powerJson 权限信息，Json格式字符串
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean updateRolePower(int roleId, String powerJson) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("powerJson", powerJson);
		
		int result = 0;
		try {
			result = sqlSessionTemplate.update("BackerRolePower_updateRolePower", map);
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
     * 删除角色的权限（删除角色下的全部权限）
     * @param roleId 角色Id
     * @return 操作成功：返回true，操作失败：返回false
     */
	public boolean deleteRolePowerByRoleId(int roleId) {
		int result = 0;
		try {
			result = sqlSessionTemplate.delete("BackerRolePower_deleteRolePowerByRoleId", roleId);
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
