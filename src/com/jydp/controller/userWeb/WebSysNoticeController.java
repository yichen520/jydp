package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.service.ISystemNoticeService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 系统公告
 * @Author: xzp
 */
@Controller
@RequestMapping("/web/webSystemNotice")
@Scope(value = "prototype")
public class WebSysNoticeController {

    /**  系统公告 */
    @Autowired
    private ISystemNoticeService systemNoticeService;

    /** 系统公告展示  */
    @RequestMapping(value = "/show")
    public @ResponseBody JsonObjectBO show(@RequestParam(value ="pageNumber")String pageNumber) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        jsonObjectBO.setData(showList(pageNumber));
        jsonObjectBO.setCode(SystemMessageConfig.REDIRECT_TO_SYSNOTICE_CODE);
        jsonObjectBO.setMessage(SystemMessageConfig.SYSTEM_MESSAGE__GET_SUCCESS);

        return jsonObjectBO;
    }

    /**  系统公告列表查询 */
    public JSONObject showList(String pageNumberInt) {
        String pageNumberStr = StringUtil.stringNullHandle(pageNumberInt);

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemNoticeService.countSystemNoticeForUser();
        int pageSize = 20;

        List<SystemNoticeDO> systemNoticeList = null;
        if (totalNumber > 0) {
            systemNoticeList = systemNoticeService.listSystemNoticeForUser(pageNumber, pageSize);
        }

        if (systemNoticeList != null && systemNoticeList.size() > 0) {
            for (SystemNoticeDO systemNotice : systemNoticeList) {
                String noticeTitle = systemNotice.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemNotice.setNoticeTitle(noticeTitle);
                }
            }
        }

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("systemNoticeList",systemNoticeList);
        jsonObject.put("pageNumber", pageNumber);
        jsonObject.put("totalNumber", totalNumber);
        jsonObject.put("totalPageNumber", totalPageNumber);
        return jsonObject;
    }

    /**  打开系统公告详情页面 */
    @RequestMapping(value = "/showNoticeDetail/{idStr}", method = RequestMethod.GET)
    public @ResponseBody JsonObjectBO showNoticeDetail(@PathVariable String idStr) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jsonObject = new JSONObject();

        if(!StringUtil.isNotNull(idStr)){
            jsonObjectBO.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            jsonObjectBO.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            jsonObjectBO.setData(  showList("0"));
            return  jsonObjectBO;
        }

        int id = 0;
        String reg = "[0-9]*";
        if (idStr.length() < 11 && Pattern.matches(reg,idStr)) {
            id = Integer.parseInt(idStr);
        }
        SystemNoticeDO systemNotice = systemNoticeService.getSystemNoticeById(id);
        if (systemNotice != null) {
            String noticeTitle = systemNotice.getNoticeTitle();
            if (StringUtil.isNotNull(noticeTitle)) {
                noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                systemNotice.setNoticeTitle(noticeTitle);
            }
        }
        jsonObject.put("systemNotice",systemNotice);
        jsonObjectBO.setData(jsonObject);
        jsonObjectBO.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        jsonObjectBO.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        return jsonObjectBO;
    }
}
