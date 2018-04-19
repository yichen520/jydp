package com.iqmkj.utils;

import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import org.apache.commons.collections4.CollectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller返回工具
 * @author njx
 **/
public class ResponseUtils {

    /**
     * 设置返回数据到jsonObjectBO
     * @param code:状态码
     * @param message:返回信息
     * @param jsonObject:返回数据对象
     * @param jsonObjectBO:数据封装到的对象
     * @return 封装后的对象
     */
    public static JsonObjectBO setResp(int code, String message, JSONObject jsonObject, JsonObjectBO jsonObjectBO) {
        if (jsonObjectBO == null) {
            jsonObjectBO = new JsonObjectBO();
        }
        //参数设置
        jsonObjectBO.setCode(code);
        jsonObjectBO.setMessage(message);
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        jsonObjectBO.setData(jsonObject);
        return jsonObjectBO;
    }

}
