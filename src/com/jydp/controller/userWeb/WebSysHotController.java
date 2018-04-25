package com.jydp.controller.userWeb;


import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.service.ISystemHotService;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 热门话题
 * @Author: xzp
 */
@Controller
@RequestMapping("/web/webSystemHot")
@Scope(value = "prototype")
public class WebSysHotController {

    /**  热门话题 */
    @Autowired
    private ISystemHotService systemHotService;

    /**  热门话题展示  */
    @RequestMapping("/show")
    public @ResponseBody
    JsonObjectBO show(@RequestBody String requestJson) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();

        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String pageNumberStr = (String) requestJsonObject.get("pageNumber");
        jsonObjectBO.setData(showList(pageNumberStr));
        jsonObjectBO.setCode(SystemMessageConfig.REDIRECT_TO_SYSHOT_CODE);
        jsonObjectBO.setMessage(SystemMessageConfig.SYSTEM_MESSAGE__GET_SUCCESS);

        return jsonObjectBO;
    }

    /**  热门话题列表查询 */
    public JSONObject showList(String pageNumberInt) {
        String pageNumberStr = StringUtil.stringNullHandle(pageNumberInt);

        int pageNumber = 1;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int totalNumber = systemHotService.countSystemHotForUser();
        //int pageSize = 20;
        int pageSize=5;

        List<SystemHotDO> systemHotList = null;
        if (totalNumber > 0) {
            systemHotList = systemHotService.listSystemHotForUser(pageNumber-1, pageSize);
        }

        if (systemHotList != null && systemHotList.size() > 0) {
            for (SystemHotDO systemHot : systemHotList) {
                String noticeTitle = systemHot.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemHot.setNoticeTitle(noticeTitle);
                }
            }
        }

        int totalPageNumber = (int)Math.ceil(totalNumber / (pageSize * 1.0));
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("systemHotList",systemHotList);
        jsonObject.put("pageNumber", pageNumber);
        jsonObject.put("totalNumber", totalNumber);
        jsonObject.put("totalPageNumber", totalPageNumber);
        return jsonObject;
    }

    /**  打开热门话题详情页面 */
    @RequestMapping(value = "/showHotDetail/{idStr}", method = RequestMethod.GET)
    public @ResponseBody JsonObjectBO showHotDetail( @PathVariable String idStr) {
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
        SystemHotDO systemHot = systemHotService.getSystemHotById(id);
        if (systemHot != null) {
            String noticeTitle = systemHot.getNoticeTitle();
            if (StringUtil.isNotNull(noticeTitle)) {
                noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                systemHot.setNoticeTitle(noticeTitle);
            }
        }

        jsonObject.put("systemHot",systemHot);
        jsonObjectBO.setData(jsonObject);
        jsonObjectBO.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        jsonObjectBO.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        return jsonObjectBO;
    }
}
