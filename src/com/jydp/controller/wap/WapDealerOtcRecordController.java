package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionUserDealDO;
import com.jydp.entity.VO.OtcTransactionUserDealVO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionUserDealService;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 场外交易用户成交记录-经销商
 * @Author: zym
 */
@Controller
@RequestMapping("/userWap/dealerOtcRecord")
@Scope(value = "prototype")
class WapDealerOtcRecordController {

    /** 场外交易成交记录 **/
    @Autowired
    private IOtcTransactionUserDealService otcTransactionUserDealService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    @RequestMapping("/show.htm")
    public String show(HttpServletRequest request){
        showList(request);
        request.setAttribute("code", 1);
        request.setAttribute("message", "查询成功!");
        return "page/wap/sellerOutRecord";
    }

    /** 查询记录 **/
    public void showList(HttpServletRequest request){

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        int userId = userSession.getUserId();

        String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
        int pageNumber = 0;

        if (StringUtil.isNotNull(pageNumberStr)) {
            pageNumber = Integer.parseInt(pageNumberStr);
        }

        int pageSize = 20;

        int totalNumber = otcTransactionUserDealService.countOtcTransactionUserDeallistByDealerId(userId,null,0,0,null,null,0,0);

        int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
        if (totalPageNumber <= 0) {
            totalPageNumber = 1;
        }
        if (totalPageNumber <= pageNumber) {
            pageNumber = totalPageNumber - 1;
        }

        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if (totalNumber > 0) {
            otcTransactionUserDealList = otcTransactionUserDealService.getOtcTransactionUserDeallistByDealerId(userId,null,0,0,null,null,0,0,pageNumber,pageSize);

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
        int totalNumber = otcTransactionUserDealService.countOtcTransactionUserDeallistByDealerId(userBo.getUserId(),null,0,0,null,null,0,0);
        int totalPageNumber = (int) Math.ceil(totalNumber/1.0/pageSize);   //总页码数
        if(totalPageNumber <= 0){
            totalPageNumber = 1;
        }
        if(pageNumber >= totalPageNumber){
            pageNumber = totalPageNumber - 1;
        }
        List<OtcTransactionUserDealVO> otcTransactionUserDealList = null;
        if(totalNumber > 0){
            otcTransactionUserDealList = otcTransactionUserDealService.getOtcTransactionUserDeallistByDealerId(userBo.getUserId(),null,0,0,null,null,0,0,pageNumber,pageSize);
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

    /** 经销商回购币-确认收货 **/
    @RequestMapping(value = "/confirmTakeCoin.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO confirmTakeCoin(HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));

        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            responseJson.setCode(2);
            responseJson.setMessage("用户操作频繁");
            return responseJson;
        }

        if (!StringUtil.isNotNull(otcOrderNo)) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误");
            return responseJson;
        }

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        //判断用户是否为经销商
        if(userSession.getIsDealer() != 2){//不是经销商
            responseJson.setCode(3);
            responseJson.setMessage("当前用户不是经销商");
            return responseJson;
        }

        OtcTransactionUserDealDO otcTransactionUserDeal =  otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(otcOrderNo);

        if (otcTransactionUserDeal == null) {
            responseJson.setCode(3);
            responseJson.setMessage("该笔订单不存在");
            return responseJson;
        }

        String otcPendingOrderNo = otcTransactionUserDeal.getOtcPendingOrderNo();

        responseJson = otcTransactionUserDealService.dealerConfirmTakeForBuyBack(otcOrderNo,otcPendingOrderNo,userSession.getUserId());

        return responseJson;
    }

    /** 经销商出售币-确认收款 **/
    @RequestMapping(value = "/confirmTakeMoney.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO confirmTakeMoney(HttpServletRequest request){
        JsonObjectBO responseJson = new JsonObjectBO();

        String otcOrderNo = StringUtil.stringNullHandle(request.getParameter("otcOrderNo"));

        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            responseJson.setCode(2);
            responseJson.setMessage("用户操作频繁");
            return responseJson;
        }

        if (!StringUtil.isNotNull(otcOrderNo)) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误");
            return responseJson;
        }

        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            responseJson.setCode(4);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        //判断用户是否为经销商
        if(userSession.getIsDealer() != 2){//不是经销商
            responseJson.setCode(3);
            responseJson.setMessage("当前用户不是经销商");
            return responseJson;
        }

        OtcTransactionUserDealDO otcTransactionUserDeal =  otcTransactionUserDealService.getOtcTransactionUsealByOrderNo(otcOrderNo);

        if (otcTransactionUserDeal == null) {
            responseJson.setCode(3);
            responseJson.setMessage("该笔订单不存在");
            return responseJson;
        }

        responseJson = otcTransactionUserDealService.dealerConfirmTakeForSellCoin(otcTransactionUserDeal,userSession.getUserId());
        return responseJson;
    }

    /** 查看详情*/
    @RequestMapping(value="/sellerOtcDetail.htm", method= RequestMethod.POST)
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

        OtcTransactionUserDealVO otcTransactionUserDeal = otcTransactionUserDealService.getOtcTransactionUsealForSell(userBo.getUserId(), otcOrderNo);
        if(otcTransactionUserDeal == null){
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数错误");
            return "page/wap/tradeOut";
        }

        request.setAttribute("otcTransactionUserDeal", otcTransactionUserDeal);
        return "page/wap/sellerOtcDetail";
    }

    /**
     * 跳转到我的记录页面
     */
    @RequestMapping(value = "/showMyRecord")
    public String showMyRecord() {
        return "page/wap/myRecord";
    }

}
