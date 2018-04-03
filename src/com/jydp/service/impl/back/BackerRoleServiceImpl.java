package com.jydp.service.impl.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.dao.IBackerRoleDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.back.BackerRoleDO;
import com.jydp.entity.DO.back.BackerRolePowerDO;
import com.jydp.entity.DO.back.BackerRolePowerMapDO;
import com.jydp.entity.DTO.BackerRoleDTO;
import com.jydp.entity.VO.BackerRoleVO;
import com.jydp.service.IBackerRolePowerMapService;
import com.jydp.service.IBackerRolePowerService;
import com.jydp.service.IBackerRoleService;
import com.jydp.service.IBackerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 账号角色
 * @author sy
 */
@Service("backerRoleService")
public class BackerRoleServiceImpl implements IBackerRoleService {

	/** 账号角色  */
	@Autowired
	private IBackerRoleDao backerRoleDao;
	
	/** 账号角色权限  */
	@Autowired
	private IBackerRolePowerService backerRolePowerService;
	
	/** 角色权限映射  */
	@Autowired
	private IBackerRolePowerMapService backerRolePowerMapService;

	/** 后台管理员 */
	@Autowired
	private IBackerService backerService;

	/**
	 * 新增账号角色
	 * @param backerRole 待新增的账号角色信息
	 * @return 操作成功:返回账号角色id, 操作失败:返回null
	 */
	public int insertRole(BackerRoleDO backerRole) {
		return backerRoleDao.insertRole(backerRole);
	}

	/**
	 * 添加账号角色（后台操作）
	 * @param backerRole 待新增的账号角色信息
	 * @param backerRolePower 待新增的账号角色权限信息
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	@Transactional
	public boolean addRoleForBacker(BackerRoleDO backerRole, BackerRolePowerDO backerRolePower) {
		boolean executeSuccess = true;
		//新增账号角色
		int roleId = 0;
		if (executeSuccess) {
			roleId = insertRole(backerRole);
		}
		if (roleId <= 0) {
			return false;
		}
		//新增账号角色权限
		if (executeSuccess) {
			backerRolePower.setRoleId(roleId);
			executeSuccess = backerRolePowerService.insertRolePower(backerRolePower);
		}
		if (!executeSuccess) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return executeSuccess;
	}
	
	/**
	 * 查询账号角色,根据角色Id
	 * @param roleId 角色Id
	 * @return 操作成功:返回账号角色信息, 操作失败:返回null
	 */
	public BackerRoleDO getRoleById(int roleId) {
		return backerRoleDao.getRoleById(roleId);
	}

	/**
	 * 查询账号角色列表
	 * @return 操作成功:返回账号角色列表, 操作失败:返回null
	 */
	public List<BackerRoleDO> listRole() {
		return backerRoleDao.listRole();
	}

	/**
	 * 查询账号角色权限列表
	 * @param backerId 后台管理员Id
	 * @return 操作成功：返回账号角色权限列表，操作失败：返回null
	 */
	public List<BackerRoleDTO> listRolePower(int backerId) {
		return backerRoleDao.listRolePower(backerId);
	}
	
	/**
	 * 查询账号角色权限
	 * @param roleId 角色Id
	 * @return 操作成功：返回账号角色权限，操作失败：返回null
	 */
	public BackerRoleDTO getRolePower(int roleId) {
		return backerRoleDao.getRolePower(roleId);
	}
	
	/**
	 * 查询角色列表（后台操作）
	 * @param backerId 后台管理员Id
	 * @return 操作成功：返回角色列表，操作失败：返回null
	 */
	public List<BackerRoleVO> listRoleInfor(int backerId) {
		List<BackerRoleVO> backerRoleVOList = new ArrayList<BackerRoleVO>();
		
		//查询账号角色列表
		List<BackerRoleDTO> backerRoleList = listRolePower(backerId);
		if (CollectionUtils.isEmpty(backerRoleList)) {
			return backerRoleVOList;
		}
		//查询角色权限映射全部信息
		List<BackerRolePowerMapDO> backerRolePowerMapList = backerRolePowerMapService.listIsocRolePowerAll();
		if (CollectionUtils.isEmpty(backerRolePowerMapList)) {
			return backerRoleVOList;
		}
		Map<Integer, BackerRolePowerMapDO> backerRolePowerMap = backerRolePowerMapList.stream().collect(
	            Collectors.toMap(BackerRolePowerMapDO::getPowerId, (p) -> p));
		
		for (BackerRoleDTO backerRole : backerRoleList) {
			JSONObject jsonData = JSONObject.parseObject(backerRole.getPowerJson());
			if (jsonData == null || jsonData.isEmpty()) {
				continue;
			}
			
			Map<Integer, String> twoPowerMap = new HashMap<Integer, String>();
			Map<Integer, String> threePowerMap = new HashMap<Integer, String>();
			for(Entry<String, Object> json : jsonData.entrySet()) {
		        int roleId = Integer.parseInt(json.getKey());
		        int twoPowerId = roleId/100;//二级权限
		        twoPowerId = twoPowerId * 100;

				BackerRolePowerMapDO backerRolePower = backerRolePowerMap.get(roleId);
		        if (backerRolePower == null) {
					continue;
				}
		        //二级权限
		        if (backerRolePower.getPowerLevel() == 2 && roleId == twoPowerId) {
		        	 twoPowerMap.put(twoPowerId, backerRolePower.getPowerName());
				}
		        //三级权限
                if (backerRolePower.getPowerLevel() == 3) {
                	StringBuffer threePower = new StringBuffer();
                	if (threePowerMap.containsKey(twoPowerId)) {
                		String powerStr = threePowerMap.get(twoPowerId);
                		threePower.append(powerStr + backerRolePower.getPowerName() + ",");
                		threePowerMap.put(twoPowerId, threePower.toString());
                		continue;
					}
                	threePower.append(backerRolePower.getPowerName() + ",");
                	threePowerMap.put(twoPowerId, threePower.toString());
				}
		    }
			
			if (twoPowerMap == null || twoPowerMap.isEmpty()) {
				return backerRoleVOList;
			}
			
			StringBuffer roleData = new StringBuffer();
			//二级权限追加
			for (Entry<Integer, String> entry : twoPowerMap.entrySet()) {
				String threePowerData = threePowerMap.get(entry.getKey());
				if (!StringUtil.isNotNull(threePowerData)) {
					continue;
				}
				
				StringBuffer twoPower = new StringBuffer();
				//开头
				twoPower.append("[");
				twoPower.append(entry.getValue());
				twoPower.append(":");
				twoPower.append(threePowerData);
				//结尾
				twoPower.deleteCharAt(twoPower.length() - 1);
				twoPower.append("],");
				roleData.append(twoPower);
			}
			//总数据结尾处理
			if (roleData.length() > 0) {
				roleData.deleteCharAt(roleData.length() - 1);
			}

			BackerRoleVO backerRoleVO = new BackerRoleVO();
			backerRoleVO.setRoleId(backerRole.getRoleId());
			backerRoleVO.setRoleName(backerRole.getRoleName());
			backerRoleVO.setRoleData(roleData.toString());
			backerRoleVOList.add(backerRoleVO);
		}
		return backerRoleVOList;
	}

	/**
	 * 修改账号角色名称
	 * @param roleId 角色Id
	 * @param roleName 新角色名称
	 * @return 操作成功:返回true, 操作失败:返回false
	 */
	public boolean updateRoleNameById(int roleId, String roleName) {
		return backerRoleDao.updateRoleNameById(roleId, roleName);
	}

	/**
	 * 修改账号角色权限（后台操作）
	 * @param roleId 角色Id
	 * @param roleName 新角色名称
	 * @param powerJson 新权限信息，Json格式字符串
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	@Transactional
	public boolean updateRoleForBacker(int roleId, String roleName, String powerJson) {
		boolean executeSuccess = true;
		//修改账号角色名称
		if (executeSuccess) {
			executeSuccess = updateRoleNameById(roleId, roleName);
		}
		//修改账号角色权限
		if (executeSuccess) {
			executeSuccess = backerRolePowerService.updateRolePower(roleId, powerJson);
		}
		if (!executeSuccess) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return executeSuccess;
	}
	
	/**
	 * 删除账号角色
	 * @param roleId 角色Id
	 * @return 操作成功:返回true, 操作失败:返回false
	 */
	public boolean deleteRoleById(int roleId) {
		return backerRoleDao.deleteRoleById(roleId);
	}

	/**
	 * 删除角色（后台操作）
	 * @param roleId 角色Id
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	@Transactional
	public JsonObjectBO deleteRoleForBacker(int roleId) {
		JsonObjectBO resultData = new JsonObjectBO();
		
		boolean executeSuccess = true;
		//删除账号角色
		if (executeSuccess) {
			executeSuccess = deleteRoleById(roleId);
		}
		//删除角色的权限
		if (executeSuccess) {
			executeSuccess = backerRolePowerService.deleteRolePowerByRoleId(roleId);
		}
		if (!executeSuccess) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultData.setCode(5);
			resultData.setMessage("操作失败");
			return resultData;
		}
		
		resultData.setCode(1);
		resultData.setMessage("操作成功");
		return resultData;
	}
	
}
