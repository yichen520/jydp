package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * web端首页
 * @author yk
 */
@Controller
@RequestMapping("/web/user")
@Scope(value = "prototype")
public class HomePageController {

    /** web端首页 */
    @Autowired
    private IHomePageService homePageService;

    @RequestMapping("/homePage")
    public String getHomePageData(HttpServletRequest request){
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jsonObject = new JSONObject();

        UserSessionBO userSession = UserWebInterceptor.getUser(request);

        //查询首页广告列表
        List<SystemAdsHomepagesDO> systemAdsHomepagesDOList = homePageService.getSystemAdsHomepageList();

        //查询系统公告列表
        List<SystemNoticeDO> systemNoticeDOList = homePageService.getSystemNoticeList();

        //查询热门话题列表
        List<SystemHotDO> systemHotDOList = homePageService.getSystemHotList();

        //查询合作商家列表
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerDOList = homePageService.getSystemBusinessesPartnerList();

        request.setAttribute("userAccount",userSession.getUserAccount());
        request.setAttribute("systemAdsHomepagesDOList",systemAdsHomepagesDOList);
        request.setAttribute("systemNoticeDOList",systemNoticeDOList);
        request.setAttribute("systemHotDOList",systemHotDOList);
        request.setAttribute("systemBusinessesPartnerDOList",systemBusinessesPartnerDOList);

        return "web/homePage";
    }
}
