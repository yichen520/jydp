package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;

import com.jydp.dao.IBackerRolePowerMapDao;
import com.jydp.entity.DO.back.BackerRolePowerMapDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限映射
 * @author sy
 *
 */
@Repository
public class BackerRolePowerMapDaoImpl implements IBackerRolePowerMapDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 查询角色权限映射
	 * @param powerId 权限Id
	 * @return 操作成功：角色权限映射信息，操作失败：返回null
	 */
	public BackerRolePowerMapDO getRolePowerMap(int powerId){
		BackerRolePowerMapDO rolePowerMapDO = null;
		try {
			rolePowerMapDO = sqlSessionTemplate.selectOne("BackerRolePowerMap_getRolePowerMap", powerId);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		
		return rolePowerMapDO;
	}
	
	/**
	 * 查询角色权限映射全部信息
	 * @return 操作成功：返回角色权限映射全部信息，操作失败：返回null
	 */
	public List<BackerRolePowerMapDO> listRolePowerAll() {
		List<BackerRolePowerMapDO> rolePowerMapList = null;
		try {
			rolePowerMapList = sqlSessionTemplate.selectList("BackerRolePowerMap_listRolePowerAll");
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		return rolePowerMapList;
	}
	
}
