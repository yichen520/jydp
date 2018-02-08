package com.jydp.controller.back;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.MD5Util;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.back.BackerDO;
import com.jydp.entity.DO.back.BackerRoleDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IBackerRoleService;
import com.jydp.service.IBackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 后台管理员
 * @author sy
 *
 */
@Controller
@RequestMapping("/backerWeb/backerAccount")
@Scope(value="prototype")
public class BackerAccountController {

    /** 后台管理员 */
    @Autowired
    private IBackerService backerService;
    
    /** 系统角色 */
    @Autowired
    private IBackerRoleService backerRoleService;

    /** 展示 后台管理员页面 */
    @RequestMapping(value = "/show.htm")
    public String show(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131101);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        showList(request, backerSession.getBackerId());
        return "page/back/backerAccount";
    }

    /** 分页查询 后台管理员数据  */
    private void showList(HttpServletRequest request, int backerId) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = backerService.countBacker();
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }

        List<BackerDO> backerList = null;
        if (totalNumber > 0) {
            backerList = backerService.listBackerByPage(pageNumber, pageSize);
        }

        List<BackerRoleDO> roleList = backerRoleService.listRole();

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);

        request.setAttribute("back_backerId", backerId);
        request.setAttribute("backerList", backerList);
        request.setAttribute("roleList", roleList);
        //当前页面的权限标识
        request.getSession().setAttribute("backer_rolePowerId", 131100);
    }

    /** 新增后台账号 */
    @RequestMapping(value="/insert.htm", method= RequestMethod.POST)
    public String insert(HttpServletRequest request){
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131102);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        String backerAccount = StringUtil.stringNullHandle(request.getParameter("addBackerAccount"));
        String password = StringUtil.stringNullHandle(request.getParameter("addBackerPassword"));
        String repeatPassword = StringUtil.stringNullHandle(request.getParameter("addBackerRepeatPassword"));
        String roleIdStr = StringUtil.stringNullHandle(request.getParameter("addRoleId"));
        
        if (!StringUtil.isNotNull(backerAccount) || !StringUtil.isNotNull(password)
                || !StringUtil.isNotNull(repeatPassword) || !StringUtil.isNotNull(roleIdStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }
        
        if (!password.equals(repeatPassword)) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "两次密码输入不一致！");
            return "page/back/index";
        }

        boolean userIsExist = backerService.validateBackerExist(backerAccount);
        if (userIsExist) {
            showList(request, backerSession.getBackerId());
            request.setAttribute("code", 3);
            request.setAttribute("message", "该帐号已存在！");
            return "page/back/backerAccount";
        }
        
        password = MD5Util.toMd5(password);
        int roleId = Integer.parseInt(roleIdStr);
        
        BackerDO backer = new BackerDO();
        backer.setBackerAccount(backerAccount);  //后台管理员账号
        backer.setPassword(password);  //后台管理员密码
        backer.setRoleId(roleId);  //角色ID
        backer.setAccountStatus(1);  //账号状态
        backer.setAddTime(DateUtil.getCurrentTime());  //添加时间
        
        boolean insertResult = backerService.insertBacker(backer);
        if (insertResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "新增成功！");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "新增失败！");
        }
        
        showList(request, backerSession.getBackerId());
        return "page/back/backerAccount";
    }

    /** 验证账号是否存在 */
    @RequestMapping(value = "/validateAccount", method = RequestMethod.POST)
    public @ResponseBody
    JsonObjectBO validateAccount(HttpServletRequest request) {
        JsonObjectBO responsJson = new JsonObjectBO();

        String backerAccount = StringUtil.stringNullHandle(request.getParameter("backerAccount"));
        if (!StringUtil.isNotNull(backerAccount)) {
            responsJson.setCode(3);
            responsJson.setMessage("参数错误");
            return responsJson;
        }

        boolean validateResult = backerService.validateBackerExist(backerAccount);
        if (!validateResult) {
            responsJson.setCode(1);
            responsJson.setMessage("验证通过");
        } else {
            responsJson.setCode(5);
            responsJson.setMessage("该账号已存在，请重新输入");
        }
        return responsJson;
    }

    /** 修改后台管理员账号角色 */
    @RequestMapping(value="/updateRole.htm", method= RequestMethod.POST)
    public String updateRole(HttpServletRequest request) {
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131103);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }

        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        
        String backerIdStr = StringUtil.stringNullHandle(request.getParameter("updateRoleBackerId"));
        String roleIdStr = StringUtil.stringNullHandle(request.getParameter("updateRoleId"));
        
        if(!StringUtil.isNotNull(backerIdStr) || !StringUtil.isNotNull(roleIdStr)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }
        
        int roleId = Integer.parseInt(roleIdStr);
        int backerId = Integer.parseInt(backerIdStr);
        
        boolean updateResult = backerService.updateBackerRole(backerId, roleId);
        if(updateResult){
            request.setAttribute("code", 1);
            request.setAttribute("message", "修改成功！");
        }else{
            request.setAttribute("code", 5);
            request.setAttribute("message", "修改失败！");
        }

        showList(request, backerSession.getBackerId());
        return "page/back/backerAccount";
    }
    
    /** 重置密码 ,默认123456*/
    @RequestMapping(value = "/resetPassword.htm")
    public String resetPassword(HttpServletRequest request){
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131107);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        String backerIdStr = StringUtil.stringNullHandle(request.getParameter("resetPasswordBackerId"));
        String password = MD5Util.toMd5("123456");
        
        if(!StringUtil.isNotNull(backerIdStr)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }
        int backerId = Integer.parseInt(backerIdStr);
        
        boolean resetResult = backerService.resetPassword(backerId, password);
        if(resetResult){
            request.setAttribute("code", 1);
            request.setAttribute("message", "重置密码成功！");
        }else{
            request.setAttribute("code", 5);
            request.setAttribute("message", "重置密码失败！");
        }

        showList(request, backerSession.getBackerId());
        return "page/back/backerAccount";
    }
    
    /** 修改密码 */
    @RequestMapping(value = "/updatePassword.htm", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request){
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }
        
        //修改密码不需要权限(修改自己的密码)
        String oldPassword = StringUtil.stringNullHandle(request.getParameter("updateBackerOldPassword"));
        String newPassword = StringUtil.stringNullHandle(request.getParameter("updateBackerNewPassword"));
        String repeatPassword = StringUtil.stringNullHandle(request.getParameter("updateBackerRepeatPassword"));
        
        if(!StringUtil.isNotNull(oldPassword) || !StringUtil.isNotNull(newPassword)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }
        if (!newPassword.equals(repeatPassword)) {
            request.setAttribute("code", 3);
            request.setAttribute("message", "两次密码输入不一致！");
            return "page/back/index";
        }
        
        oldPassword = MD5Util.toMd5(oldPassword);
        newPassword = MD5Util.toMd5(newPassword);
        
        boolean updateResult = backerService.updatePassword(backerSession.getBackerId(), oldPassword, newPassword);
        if(updateResult){
            BackerWebInterceptor.loginOut(request);
            request.setAttribute("code", 4);
            request.setAttribute("message", "修改成功！请重新登录");
            return "page/back/login";
        }else{
            request.setAttribute("code", 5);
            request.setAttribute("message", "修改密码失败！");
            showList(request, backerSession.getBackerId());
            return "page/back/backerAccount";
        }
    }
    
    /** 修改后台账号状态，启用(1：启用，2：禁用，-1：删除 )*/
    @RequestMapping(value = "/startUp.htm")
    public String startUp(HttpServletRequest request){
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131105);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        String backerIdStr = StringUtil.stringNullHandle(request.getParameter("enbaleBackerId"));
        if (!StringUtil.isNotNull(backerIdStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }
        
        int backerId = Integer.parseInt(backerIdStr);
        int accountStatus = 1;
        boolean updateResult = backerService.updateAccountStatus(backerId, accountStatus);
        if (updateResult) {
            request.setAttribute("code", 1);
            request.setAttribute("message", "启用成功！");
        } else {
            request.setAttribute("code", 5);
            request.setAttribute("message", "启用失败！");
        }

        showList(request, backerSession.getBackerId());
        return "page/back/backerAccount";
    }

    /** 修改后台账号状态，禁用(1：启用，2：禁用，-1：删除 )*/
    @RequestMapping(value = "/forbidden.htm")
    public String forbidden(HttpServletRequest request){
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131106);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        String backerIdStr = StringUtil.stringNullHandle(request.getParameter("disableBackerId"));
        if (!StringUtil.isNotNull(backerIdStr)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }

        int backerId = Integer.parseInt(backerIdStr);
        if(backerId == backerSession.getBackerId()){
            request.setAttribute("code", 3);
            request.setAttribute("message", "您不能禁用自己");
            showList(request, backerSession.getBackerId());
            return "page/back/backerAccount";
        }

        int accountStatus = 2;
        boolean updateResult = backerService.updateAccountStatus(backerId, accountStatus);
        if(updateResult){
            request.setAttribute("code", 1);
            request.setAttribute("message", "禁用成功！");
        }else{
            request.setAttribute("code", 5);
            request.setAttribute("message", "禁用失败！");
        }

        showList(request, backerSession.getBackerId());
        return "page/back/backerAccount";
    }

    /** 修改后台账号状态，删除(1：启用，2：禁用，-1：删除 )*/
    @RequestMapping(value = "/delete.htm")
    public String delete(HttpServletRequest request){
        //业务功能权限
        boolean havePower = BackerWebInterceptor.validatePower(request, 131104);
        if (!havePower) {
            request.setAttribute("code", 6);
            request.setAttribute("message", "您没有该权限");
            request.getSession().setAttribute("backer_rolePowerId", 0);
            return "page/back/index";
        }
        BackerSessionBO backerSession = BackerWebInterceptor.getBacker(request);
        if (backerSession == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "登录过期");
            return "page/back/login";
        }

        String backerIdStr = StringUtil.stringNullHandle(request.getParameter("deleteBackerId"));
        
        if(!StringUtil.isNotNull(backerIdStr)){
            request.setAttribute("code", 2);
            request.setAttribute("message", "未接收到参数！");
            return "page/back/index";
        }

        int backerId = Integer.parseInt(backerIdStr);
        if(backerId == backerSession.getBackerId()){
            request.setAttribute("code", 3);
            request.setAttribute("message", "您不能删除自己");
            showList(request, backerSession.getBackerId());
            return "page/back/backerAccount";
        }

        int accountStatus = -1;
        boolean updateResult = backerService.updateAccountStatus(backerId, accountStatus);
        if(updateResult){
            request.setAttribute("code", 1);
            request.setAttribute("message", "删除成功！");
        }else{
            request.setAttribute("code", 5);
            request.setAttribute("message", "删除失败！");
        }

        showList(request, backerSession.getBackerId());
        return "page/back/backerAccount";
    }
}
