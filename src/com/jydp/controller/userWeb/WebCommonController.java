package com.jydp.controller.userWeb;

import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import config.PhoneAreaConfig;
import net.sf.json.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
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
    public JsonObjectBO getPhoneArea(){
        JsonObjectBO responseJson = new JsonObjectBO();
        JSONObject jsonObject = new JSONObject();

        Map<String, String> phoneAreaMap = PhoneAreaConfig.phoneAreaMap;
        String jsonObjectStr = JSONObject.toJSONString(phoneAreaMap);
        jsonObject.put("phoneAreaMap",jsonObjectStr);

        responseJson.setCode(1);
        responseJson.setMessage("查询成功");
        responseJson.setData(jsonObject);

        return responseJson;
    }
}
