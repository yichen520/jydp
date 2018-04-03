package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.VO.TransactionUserDealVO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.ITransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * wap端成交记录查询
 * @author cyfIverson
 * @create 2018-03-27
 */
@Controller
@RequestMapping("/userWap/wapDealRecord")
@Scope(value = "prototype")
public class WapDealRecordController {
    /**
     * 查询用户成交记录
     */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    /**
     * 跳转用户成交记录页面
     */
    @RequestMapping("/show.htm")
    public String showAccountRecordPage(HttpServletRequest request) {
        UserSessionBO userSession = UserWapInterceptor.getUser(request);
        if (userSession == null) {
            return "page/wap/login";
        }
        request.setAttribute("pageNumber", 0);
        return "page/wap/volume";
    }

    /**
     * 委托记录的查看详情跳转到成交记录页面
     */
    @RequestMapping(value = "/show/{pendingOrderNoStr}",method = RequestMethod.GET)
    public String showAccountRecordPageResultFor(HttpServletRequest request,@PathVariable String pendingOrderNoStr) {
        UserSessionBO userSession = UserWapInterceptor.getUser(request);
        if (userSession == null) {
            return "page/wap/login";
        }
        request.setAttribute("pendingOrderNo", pendingOrderNoStr);
        request.setAttribute("pageNumber", 0);
        return "page/wap/volume";
    }

    /**
     * 查询用户成交记录
     */
    @RequestMapping("/getAccountRecord.htm")
    public @ResponseBody
    JsonObjectBO getAccountRecordPage(HttpServletRequest request) {
        UserSessionBO userSession = UserWapInterceptor.getUser(request);
        int userId = userSession.getUserId();

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        String pendingOrderNo = StringUtil.stringNullHandle(request.getParameter("pendingOrderNo"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        //返回对象
        JSONObject jsonObject = new JSONObject();
        JsonObjectBO responseJson = new JsonObjectBO();

        int pageSize = 10;
        if (StringUtil.isNotNull(pendingOrderNo)) {
            int totalNumber = transactionUserDealService.countTransactionUserDealByPendNo(pendingOrderNo,userId);
            int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
            if (totalPageNumber <= 0) {
                totalPageNumber = 1;
            }
            if (totalPageNumber <= pageNumber) {
                pageNumber = totalPageNumber - 1;
            }

            List<TransactionUserDealVO> dealRecordList = null;
            if (totalNumber > 0) {
                dealRecordList = transactionUserDealService.listTransactionUserDealByPendNoForWap(pendingOrderNo, userId,pageNumber, pageSize);
            }

            jsonObject.put("dealRecordList", dealRecordList);
            jsonObject.put("totalPageNumber",totalPageNumber);
            responseJson.setCode(1);
            responseJson.setMessage("数据请求成功");
            responseJson.setData(jsonObject);
            return responseJson;
        }


       int totalNumber = transactionUserDealService.countUserDealForWap(userId);

        //总页数
        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }

        //对传过来的页数做处理
        if (totalPageNumber < pageNumber) {
            pageNumber = totalPageNumber-1;
        }

        List<TransactionUserDealVO> transactionUserDealList = null;
        if (totalNumber > 0) {
            transactionUserDealList = transactionUserDealService.getTransactionUserDeallistForWap(userId, pageNumber, pageSize);
        }

        jsonObject.put("dealRecordList", transactionUserDealList);
        jsonObject.put("totalPageNumber",totalPageNumber);
        responseJson.setCode(1);
        responseJson.setMessage("查询成功");
        responseJson.setData(jsonObject);
        return responseJson;
    }
}