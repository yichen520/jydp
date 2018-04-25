package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.ResponseUtils;
import com.jydp.entity.BO.JsonObjectBO;
import com.sun.org.apache.regexp.internal.RE;
import config.PhoneAreaConfig;
import config.SystemMessageConfig;
import net.sf.json.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * web2.0公用controller
 * @author cyfIverson
 * @create 2018-04-19
 */

@RestController
@RequestMapping("/web/commonResource")
@Scope(value = "prototype")
public class WebCommonController {

    /**
     * 手机区域号获取
     */
    @RequestMapping(value = "/phoneArea")
    public JsonObjectBO getPhoneArea() {
        JsonObjectBO responseJson = new JsonObjectBO();
        JSONObject jsonObject = new JSONObject();

        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        String jsonObjectStr = JSONObject.toJSONString(phoneAreaMap);
        jsonObject.put("phoneAreaMap", jsonObjectStr);

        responseJson.setCode(1);
        responseJson.setMessage("查询成功");
        responseJson.setData(jsonObject);

        return responseJson;
    }

    /**
     * 项目路径给与
     */
    @RequestMapping("/getServerName")
    public JsonObjectBO getServerName(HttpServletRequest request, HttpServletResponse response) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        JSONObject jo = new JSONObject();
        jo.put("serverName", request.getServerName());
        ResponseUtils.setResp(SystemMessageConfig.SYSTEM_CODE_SUCCESS, SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS, jo, jsonObjectBO);
        return jsonObjectBO;

    }
}
