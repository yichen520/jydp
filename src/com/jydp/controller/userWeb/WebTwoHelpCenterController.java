package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemHelpDO;
import com.jydp.service.ISystemHelpService;
import config.SystemHelpConfig;
import config.SystemMessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/web/webHelpCenter")
@Scope(value = "prototype")
public class WebTwoHelpCenterController {

    /**  帮助中心 */
    @Autowired
    private ISystemHelpService systemHelpService;

    /** 显示帮助中心页面 */
    @RequestMapping(value = "/getHelpInformation")
    public JsonObjectBO getHelpInformation(@RequestBody String requestJson) {
        JsonObjectBO responseJson = new JsonObjectBO();

        //参数获取
        JSONObject requestJsonObject = (JSONObject) JSONObject.parse(requestJson);
        String helpIdStr = (String) requestJsonObject.get("helpIdStr");

        //默认加载
        int helpId = SystemHelpConfig.COMPANY_SYNOPSIS;
        String reg = "[0-9]*";
        if (helpIdStr.length() < 11 && Pattern.matches(reg,helpIdStr)) {
            int help = Integer.parseInt(helpIdStr);
            if (SystemHelpConfig.userHelpMap.containsKey(help)) {
                helpId = Integer.parseInt(helpIdStr);
            }
        }

        SystemHelpDO systemHelpDO = systemHelpService.getSystemHelpById(helpId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("systemHelpDO", systemHelpDO);
        jsonObject.put("helpId", helpId);

        responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        responseJson.setData(jsonObject);
        return responseJson;
    }
}
