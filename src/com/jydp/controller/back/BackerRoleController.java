package com.jydp.controller.back;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.back.BackerRoleDO;
import com.jydp.entity.DO.back.BackerRolePowerDO;
import com.jydp.entity.DO.back.BackerRolePowerMapDO;
import com.jydp.entity.DTO.BackerRoleDTO;
import com.jydp.entity.DTO.BackerRolePowerMapDTO;
import com.jydp.entity.VO.BackerRolePowerMapVO;
import com.jydp.entity.VO.BackerRoleVO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IBackerRolePowerMapService;
import com.jydp.service.IBackerRoleService;
import com.jydp.service.IBackerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台角色权限
 * @author sy
 *
 */
@Controller
@RequestMapping("/backerWeb/backerRole")
@Scope(value="prototype")
public class BackerRoleController {

	/** 账号角色 */
	@Autowired
	private IBackerRoleService roleService;
	
	/** 角色权限映射  */
	@Autowired
	private IBackerRolePowerMapService rolePowerMapService;

	/** 后台管理员 */
	@Autowired
	private IBackerService backerService;
	
    /** 账号角色列表展示页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
		//业务功能权限
		boolean havePower = BackerWebInterceptor.validatePower(request, 131001);
		if (!havePower) {
			request.setAttribute("code", 6);
			request.setAttribute("message", "您没有该权限");
			request.getSession().setAttribute("backer_pagePowerId", 0);
			return "page/back/index";
		}

		BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
		if (backerSession == null) {
			request.setAttribute("code", 4);
			request.setAttribute("message", "未登录");
			return "page/back/login";
		}
		
		List<BackerRoleVO> backerRoleList = roleService.listRoleInfor(backerSession.getBackerId());
        request.setAttribute("backerRoleList", backerRoleList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_pagePowerId", 131000);
        
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if(map != null){
			if(map.containsKey("code") && map.containsKey("message")){
				request.setAttribute("code", map.get("code"));
				request.setAttribute("message", map.get("message").toString());
			}
		}
        return "page/back/role";
    }

    /** 查询全部权限 */
    private void getRolePower(HttpServletRequest request) {
    	List<BackerRolePowerMapDO> rolePowerMapList = rolePowerMapService.listIsocRolePowerAll();
		List<BackerRolePowerMapVO> rolePowerMapVOList = new ArrayList<BackerRolePowerMapVO>();
		if (CollectionUtils.isNotEmpty(rolePowerMapList)) {
			Map<Integer, List<BackerRolePowerMapDTO>> rolePowerListMap = new HashMap<Integer, List<BackerRolePowerMapDTO>>();
			
			for (BackerRolePowerMapDO rolePowerMap : rolePowerMapList) {
				int powerId = rolePowerMap.getPowerId();
				int twoPowerId = powerId/100;
				twoPowerId = twoPowerId * 100;
				String powerName = rolePowerMap.getPowerName();
				
				//二级权限
				if (rolePowerMap.getPowerLevel() == 2) {
					BackerRolePowerMapVO isocRolePower = new BackerRolePowerMapVO();
					isocRolePower.setPowerId(powerId);
					isocRolePower.setPowerName(powerName);
					rolePowerMapVOList.add(isocRolePower);
					continue;
				}
				//三级权限
				BackerRolePowerMapDTO isocRolePowerMapDTO = null;
				if (rolePowerMap.getPowerLevel() == 3) {
					isocRolePowerMapDTO = new BackerRolePowerMapDTO();
					isocRolePowerMapDTO.setPowerId(powerId);
					isocRolePowerMapDTO.setPowerName(powerName);
				}
				if (isocRolePowerMapDTO == null) {
					continue;
				}
				
				if (rolePowerListMap.containsKey(twoPowerId)) {
					List<BackerRolePowerMapDTO> isocRolePowerDTOList = rolePowerListMap.get(twoPowerId);
					isocRolePowerDTOList.add(isocRolePowerMapDTO);
					rolePowerListMap.put(twoPowerId, isocRolePowerDTOList);
					continue;
				}
				
				List<BackerRolePowerMapDTO> isocRolePowerMapDTOList = new ArrayList<BackerRolePowerMapDTO>();
				isocRolePowerMapDTOList.add(isocRolePowerMapDTO);
				rolePowerListMap.put(twoPowerId, isocRolePowerMapDTOList);
			}
			
			for (BackerRolePowerMapVO isocRolePowerMapVO : rolePowerMapVOList) {
				List<BackerRolePowerMapDTO> isocRolePowerMapDTOList = rolePowerListMap.get(isocRolePowerMapVO.getPowerId());
				if (CollectionUtils.isEmpty(isocRolePowerMapDTOList)) {
					continue;
				}
				isocRolePowerMapVO.setPowerPowerMapList(isocRolePowerMapDTOList);
			}
		}
		
		request.setAttribute("rolePowerMapVOList", rolePowerMapVOList);
    }
    
    /** 打开新增角色页面 */
    @RequestMapping(value = "/roleAddPage.htm")
    public String addRolePage(HttpServletRequest request) {
		//业务功能权限
		boolean havePower = BackerWebInterceptor.validatePower(request, 131002);
		if (!havePower) {
			request.setAttribute("code", 6);
			request.setAttribute("message", "您没有该权限");
			request.getSession().setAttribute("backer_pagePowerId", 0);
			return "page/back/index";
		}
    	
    	getRolePower(request);
        return "page/back/roleAdd";
    }
    
    /** 角色新增操作 */
    @RequestMapping(value = "/roleAdd.htm", method = RequestMethod.POST)
    public String roleAdd(HttpServletRequest request, RedirectAttributes attributes) {
		//业务功能权限
		boolean havePower = BackerWebInterceptor.validatePower(request, 131002);
		if (!havePower) {
			request.setAttribute("code", 6);
			request.setAttribute("message", "您没有该权限");
			request.getSession().setAttribute("backer_pagePowerId", 0);
			return "page/back/index";
		}

		BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
		if (backerSession == null) {
			request.setAttribute("code", 4);
			request.setAttribute("message", "未登录");
			return "page/back/login";
		}
		
        String roleName = StringUtil.stringNullHandle(request.getParameter("add_roleName"));
        String powerJson = StringUtil.stringNullHandle(request.getParameter("add_powerJson"));
		String mainPowerJson = StringUtil.stringNullHandle(request.getParameter("add_mainPowerJson"));
        if (!StringUtil.isNotNull(roleName) || !StringUtil.isNotNull(powerJson)
				|| !StringUtil.isNotNull(mainPowerJson)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接受到参数");
            return "page/back/roleAdd";
        }

		JSONObject power = JSONObject.parseObject(powerJson);
		JSONObject powerMain = JSONObject.parseObject(mainPowerJson);
		power.putAll(powerMain);

		if(power == null || power.isEmpty()){
			request.setAttribute("code", 2);
			request.setAttribute("message", "权限参数为空");
			return "page/back/roleAdd";
		}

        Timestamp addTime = DateUtil.getCurrentTime();
        //账号角色
        BackerRoleDO role = new BackerRoleDO();
		role.setRoleName(roleName);
		role.setAddTime(addTime);
        //账号角色权限
		BackerRolePowerDO rolePower = new BackerRolePowerDO();
		rolePower.setPowerJson(power.toJSONString());
		rolePower.setAddTime(addTime);
        
        boolean addRole = roleService.addRoleForBacker(role, rolePower);
        if (addRole) {
        	attributes.addFlashAttribute("code", 1);
        	attributes.addFlashAttribute("message", "操作成功");
        } else {
        	attributes.addFlashAttribute("code", 5);
        	attributes.addFlashAttribute("message", "操作失败");
        }
        return "redirect:/backerWeb/backerRole/show.htm";
    }
    
    /** 打开修改角色页面  */
    @RequestMapping(value = "/roleModifyPage.htm")
    public String roleModifyPage(HttpServletRequest request, RedirectAttributes attributes) {
		//业务功能权限
		boolean havePower = BackerWebInterceptor.validatePower(request, 131003);
		if (!havePower) {
			request.setAttribute("code", 6);
			request.setAttribute("message", "您没有该权限");
			request.getSession().setAttribute("backer_pagePowerId", 0);
			return "page/back/index";
		}
    	
        String roleIdStr = StringUtil.stringNullHandle(request.getParameter("modify_roleId"));
        if (!StringUtil.isNotNull(roleIdStr)) {
        	attributes.addFlashAttribute("code", 4);
        	attributes.addFlashAttribute("message", "参数错误");
        	return "redirect:/backerWeb/backerRole/show.htm";
        }
        
        int roleId = Integer.parseInt(roleIdStr);

		BackerRoleDTO role = roleService.getRolePower(roleId);
        if (role == null) {
        	attributes.addFlashAttribute("code", 5);
        	attributes.addFlashAttribute("message", "账号角色信息错误");
        	return "redirect:/backer/backerRole/show.htm";
        }

        request.setAttribute("role", role);

        getRolePower(request);
        return "page/back/roleModify";
    }
    
    /** 角色修改操作 */
    @RequestMapping(value = "/roleModify.htm", method = RequestMethod.POST)
    public String roleModify(HttpServletRequest request, RedirectAttributes attributes) {
		//业务功能权限
		boolean havePower = BackerWebInterceptor.validatePower(request, 131003);
		if (!havePower) {
			request.setAttribute("code", 6);
			request.setAttribute("message", "您没有该权限");
			request.getSession().setAttribute("backer_pagePowerId", 0);
			return "page/back/index";
		}
    	
        String roleIdStr = StringUtil.stringNullHandle(request.getParameter("modify_roleId"));
        String roleName = StringUtil.stringNullHandle(request.getParameter("modify_roleName"));
        String powerJson = StringUtil.stringNullHandle(request.getParameter("modify_powerJson"));
		String mainPowerJson = StringUtil.stringNullHandle(request.getParameter("modify_mainPowerJson"));
        if (!StringUtil.isNotNull(roleIdStr) || !StringUtil.isNotNull(roleName)
        		|| !StringUtil.isNotNull(powerJson) || !StringUtil.isNotNull(mainPowerJson)) {
            attributes.addFlashAttribute("code", 3);
        	attributes.addFlashAttribute("message", "参数错误");
        	return "redirect:/backerWeb/backerRole/show.htm";
        }
        
        int roleId = Integer.parseInt(roleIdStr);
		JSONObject power = JSONObject.parseObject(powerJson);
		JSONObject powerMain = JSONObject.parseObject(mainPowerJson);
		power.putAll(powerMain);
        boolean updateResult = roleService.updateRoleForBacker(roleId, roleName, power.toJSONString());
        if (updateResult) {
        	attributes.addFlashAttribute("code", 1);
        	attributes.addFlashAttribute("message", "操作成功");
        	return "redirect:/backerWeb/backerRole/show.htm";
        }
        //操作失败
		BackerRoleDTO role = roleService.getRolePower(roleId);
        if (role == null) {
        	attributes.addFlashAttribute("code", 5);
        	attributes.addFlashAttribute("message", "账号角色信息错误");
        	return "redirect:/backerWeb/backerRole/show.htm";
        }
    	
    	request.setAttribute("role", role);
        request.setAttribute("powerJson", role.getPowerJson());
        
        request.setAttribute("code", 5);
        request.setAttribute("message", "操作失败");
        return "page/back/roleModify";
    }
    
    /** 删除角色*/
    @RequestMapping(value = "/roleDelete.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO roleDelete(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
		//业务功能权限
		boolean havePower = BackerWebInterceptor.validatePower(request, 131004);
		if (!havePower) {
            responseJson.setCode(6);
            responseJson.setMessage("您没有该权限");
            return responseJson;
		}
    	
        String roleIdStr = StringUtil.stringNullHandle(request.getParameter("delete_roleId"));
        if (!StringUtil.isNotNull(roleIdStr)) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误");
            return responseJson;
        }

        int roleId = Integer.parseInt(roleIdStr);
		//验证有无用户在使用该角色
		int validateNumber = backerService.getBackerNumberByRoleId(roleId);
		if (validateNumber > 0) {
			responseJson.setCode(5);
			responseJson.setMessage("有用户正在使用该角色，无法删除。");
			return responseJson;
		}

        JsonObjectBO deleteResult = roleService.deleteRoleForBacker(roleId);

        responseJson.setCode(deleteResult.getCode());
        responseJson.setMessage(deleteResult.getMessage());
        return responseJson;
    }
	
}
