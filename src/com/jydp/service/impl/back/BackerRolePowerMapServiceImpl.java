package com.jydp.service.impl.back;

import com.jydp.dao.IBackerRolePowerMapDao;
import com.jydp.entity.DO.back.BackerRolePowerMapDO;
import com.jydp.service.IBackerRolePowerMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 角色权限映射
 * @author sy
 *
 */
@Service("isocRolePowerMapService")
public class BackerRolePowerMapServiceImpl implements IBackerRolePowerMapService {

	/** 角色权限映射 */
	@Autowired
	private IBackerRolePowerMapDao backerRolePowerMapDao;
	
	/**
	 * 查询角色权限映射
	 * @param powerId 权限Id
	 * @return 操作成功：角色权限映射信息，操作失败：返回null
	 */
	public BackerRolePowerMapDO getIsocRolePowerMap(int powerId){
		return backerRolePowerMapDao.getRolePowerMap(powerId);
	}
	
	/**
	 * 查询角色权限映射全部信息
	 * @return 操作成功：返回角色权限映射全部信息，操作失败：返回null
	 */
	public List<BackerRolePowerMapDO> listIsocRolePowerAll() {
		return backerRolePowerMapDao.listRolePowerAll();
	}
	
}
