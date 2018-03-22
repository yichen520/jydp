package com.jydp.controller.syl;


import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.service.ISylUserBoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 盛源链账号绑定
 *
 * @author sy
 */
@Controller
@RequestMapping("/syl/sylUserBound")
@Scope(value = "prototype")
public class SylUserBoundController {
    /** 交易统计记录表 */
    @Autowired
    private ISylUserBoundService sylUserBoundService;

    /** 查询当前价和保底价 */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public @ResponseBody JSONObject transfer(HttpServletRequest request) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("data", null);

        String phone = StringUtil.stringNullHandle(request.getParameter("phone"));
        String password = StringUtil.stringNullHandle(request.getParameter("password"));
        String uname = StringUtil.stringNullHandle(request.getParameter("uname"));
        String idCard = StringUtil.stringNullHandle(request.getParameter("idCard"));
        String key = StringUtil.stringNullHandle(request.getParameter("key"));



        if (!StringUtil.isNotNull(phone) || !StringUtil.isNotNull(password)|| !StringUtil.isNotNull(uname)
                || !StringUtil.isNotNull(idCard) || !StringUtil.isNotNull(key)) {
            responseJson.put("code", 2);
            responseJson.put("message", "参数错误");
            return responseJson;
        }

        return responseJson;
    }
}
