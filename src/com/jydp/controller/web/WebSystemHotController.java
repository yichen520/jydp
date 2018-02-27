package com.jydp.controller.web;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.service.ISystemHotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 热门话题
 * @Author: wqq
 */
@Controller
@RequestMapping("/userWeb/webSystemHot")
@Scope(value = "prototype")
public class WebSystemHotController {

    /**  热门话题 */
    @Autowired
    private ISystemHotService systemHotService;

    /**  热门话题展示  */
    @RequestMapping("/show")
    public String show(HttpServletRequest request) {
        showList(request);

        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return  "page/web/systemHot";
    }

    /**  热门话题列表查询 */
    public void showList(HttpServletRequest request) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemHotService.countSystemHotForUser();
        int pageSize = 20;

        List<SystemHotDO> systemHotList = null;
        if (totalNumber > 0) {
            systemHotList = systemHotService.listSystemHotForUser(pageNumber, pageSize);
        }

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);

        request.setAttribute("systemHotList", systemHotList);
    }

    /**  打开热门话题详情页面 */
    @RequestMapping("/showHotDetail")
    public String showHotDetail(HttpServletRequest request) {
        String idStr = StringUtil.stringNullHandle(request.getParameter("hotId"));

        if(!StringUtil.isNotNull(idStr)){
            showList(request);
            request.setAttribute("code", 3);
            request.setAttribute("message", "参数错误！");
            return  "page/web/systemHot";
        }

        int id = Integer.parseInt(idStr);
        SystemHotDO systemHot = systemHotService.getSystemHotById(id);

        request.setAttribute("systemHot",systemHot);
        return "page/web/systemHotDetail";
    }
}
