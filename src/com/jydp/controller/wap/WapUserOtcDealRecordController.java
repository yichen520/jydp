package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.VO.OtcTransactionPendOrderVO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 场外交易用户成交记录
 * @Author: zym
 */
@Controller
@RequestMapping("/userWap/userOtcDealRecord")
@Scope(value = "prototype")
public class WapUserOtcDealRecordController {

    /**  场外交易成交记录 */
    @Autowired
    private IOtcTransactionUserDealService otcTransactionUserDealService;

    /**  场外交易成交记录页面展示 */
    @RequestMapping("show.htm")
    public String show(HttpServletRequest request) {
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/wap/login";
        }

        showList(request, userBo);
        return "page/wap/userOtcRecord";

    }

    /**  场外交易列表查询 */
    public void showList(HttpServletRequest request, UserSessionBO userBo) {
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = otcTransactionUserDealService.numberOtcTransactionUsealByUserId(userBo.getUserId(), null, 0, 0, 0, null, null);
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }
        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if(totalNumber > 0){
            otcTransactionUserDealList = otcTransactionUserDealService.listOtcTransactionUsealByUserId(userBo.getUserId(),
                    null, 0, 0, 0, null, null, pageNumber, pageSize);
            if(otcTransactionUserDealList != null && otcTransactionUserDealList.size() > 0){
                for(OtcTransactionUserDealVO otcTransactionUserDeal : otcTransactionUserDealList){
                    double currencyTotalPrice = NumberUtil.doubleFormat(otcTransactionUserDeal.getCurrencyTotalPrice(), 2);
                    otcTransactionUserDeal.setCurrencyTotalPrice(currencyTotalPrice);
                }
            }
        }
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalNumber", totalNumber);
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("otcTransactionUserDealList", otcTransactionUserDealList);
    }

    /** 查看更多*/
    @RequestMapping(value="/showMore", method= RequestMethod.POST)
    public @ResponseBody JsonObjectBO showMore(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            responseJson.setCode(4);
            responseJson.setMessage("未登录！");
            return responseJson;
        }
        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));

        int pageNumber = 0;
        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;
        int totalNumber = otcTransactionUserDealService.numberOtcTransactionUsealByUserId(userBo.getUserId(), null, 0, 0, 0, null, null);
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }
        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if(totalNumber > 0){
            otcTransactionUserDealList = otcTransactionUserDealService.listOtcTransactionUsealByUserId(userBo.getUserId(),
                    null, 0, 0, 0, null, null, pageNumber, pageSize);
            if(otcTransactionUserDealList != null && otcTransactionUserDealList.size() > 0){
                for(OtcTransactionUserDealVO otcTransactionUserDeal : otcTransactionUserDealList){
                    double currencyTotalPrice = NumberUtil.doubleFormat(otcTransactionUserDeal.getCurrencyTotalPrice(), 2);
                    otcTransactionUserDeal.setCurrencyTotalPrice(currencyTotalPrice);
                }
            }
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("totalNumber", totalNumber);
        jsonObject.put("totalPageNumber", totalPageNumber);
        jsonObject.put("pageNumber", pageNumber);
        jsonObject.put("otcTransactionUserDealList", otcTransactionUserDealList);
        responseJson.setData(jsonObject);
        responseJson.setCode(1);
        responseJson.setMessage("查询成功");
        return responseJson;
    }

    /**  用户确认（出售 回购（收款，收货）） */
    @RequestMapping(value = "userConfirm.htm")
    public @ResponseBody JsonObjectBO userConfirm(HttpServletRequest request) {
        JsonObjectBO response = new JsonObjectBO();
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            response.setCode(4);
            response.setMessage("未登录");
            return response;
        }

        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            response.setCode(2);
            response.setMessage("操作过于频繁");
            return response;
        }

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));
        if (!StringUtil.isNotNull(otcOrderNo)){
            response.setCode(2);
            response.setMessage("参数错误");
            return response;
        }

        //查询交易记录
        OtcTransactionUserDealDO otcTransactionUserDeal = otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(otcOrderNo);
        if(otcTransactionUserDeal == null){
            response.setCode(3);
            response.setMessage("此订单不存在");
            return response;
        }

        if(otcTransactionUserDeal.getUserId() != userBo.getUserId()){
            response.setCode(3);
            response.setMessage("非法访问");
            return response;
        }
        if(otcTransactionUserDeal.getDealStatus() == 4){
            response.setCode(2);
            response.setMessage("此订单已完成");
            return response;
        }

        JsonObjectBO userConfirmation = null;
        if(otcTransactionUserDeal.getDealType() == 1){
            userConfirmation = otcTransactionUserDealService.userConfirmationOfReceiptsurchase(otcTransactionUserDeal, userBo.getUserId());
        } else if(otcTransactionUserDeal.getDealType() == 2){
            userConfirmation = otcTransactionUserDealService.userConfirmationOfReceipts(otcTransactionUserDeal, userBo.getUserId());
        } else {
            response.setCode(3);
            response.setMessage("非法类型");
            return response;
        }
        return userConfirmation;
    }

    /** 查看详情*/
    @RequestMapping(value="/userOtcDetail.htm", method= RequestMethod.POST)
    public String otcDetail(HttpServletRequest request) {
        UserSessionBO userBo = UserWebInterceptor.getUser(request);
        if (userBo == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/wap/login";
        }

        String otcOrderNo =  StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));
        if (!StringUtil.isNotNull(otcOrderNo)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        OtcTransactionUserDealVO otcTransactionUserDeal = otcTransactionUserDealService.getOtcTransactionUseal(userBo.getUserId(), otcOrderNo);
        if(otcTransactionUserDeal == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        request.setAttribute("otcTransactionUserDeal", otcTransactionUserDeal);
        return "page/wap/userOtcDetail";
    }
}
