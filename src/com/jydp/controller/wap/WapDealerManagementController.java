package com.jydp.controller.wap;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.ImageReduceUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DTO.OtcTransactionPendOrderDTO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IOtcTransactionPendOrderService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserCurrencyNumService;
import config.FileUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * wap经销商管理
 * @author zym
 **/
@Controller
@RequestMapping("/userWap/dealerManagment")
@Scope(value = "prototype")
public class WapDealerManagementController {

    /**
     * 场外交易
     */
    @Autowired
    public IOtcTransactionPendOrderService otcTransactionPendOrderService;

    /**
     * 交易币种
     */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /**
     * 用户币管理
     */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /**
     * 进入经销商管理页面
     */
    @RequestMapping("/show")
    public String show(HttpServletRequest request){
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/wap/login";
        }

        showList(request, user);
        return "page/wap/dealerManagment";
    }

    /**  经销商管理列表查询 */
    public void showList(HttpServletRequest request, UserSessionBO user) {
        if(user.getIsDealer() == 2){
            String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
            int pageNumber = 0;
            if (StringUtil.isNotNull(pageNumberStr)) {
                pageNumber = Integer.parseInt(pageNumberStr);
            }

            int pageSize = 20;
            int totalNumber = otcTransactionPendOrderService.countOtcTransactionPendOrderByUserId(user.getUserId());
            int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
            if (totalPageNumber <= 0) {
                totalPageNumber = 1;
            }
            if (totalPageNumber <= pageNumber) {
                pageNumber = totalPageNumber - 1;
            }

            List<OtcTransactionPendOrderDO> otcTransactionPendOrderList = null;
            if (totalNumber > 0) {
                otcTransactionPendOrderList = otcTransactionPendOrderService.listOtcTransactionPendOrder(user.getUserId(), pageNumber, pageSize);
            }

            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("totalNumber", totalNumber);
            request.setAttribute("totalPageNumber", totalPageNumber);
            request.setAttribute("otcTransactionPendOrderList", otcTransactionPendOrderList);
        }
    }

    /**  经销商管理列表查看更多 */
    @RequestMapping(value = "/showMore", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO showMore(HttpServletRequest request){
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            resultJson.setCode(4);
            resultJson.setMessage("未登录");
            return resultJson;
        }
        if(user.getIsDealer() == 2){
            String pageNumberStr = StringUtil.stringNullHandle(request.getParameter("pageNumber"));
            int pageNumber = 0;
            if (StringUtil.isNotNull(pageNumberStr)) {
                pageNumber = Integer.parseInt(pageNumberStr);
            }

            int pageSize = 20;
            int totalNumber = otcTransactionPendOrderService.countOtcTransactionPendOrderByUserId(user.getUserId());
            int totalPageNumber = (int) Math.ceil(totalNumber / 1.0 / pageSize);
            if (totalPageNumber <= 0) {
                totalPageNumber = 1;
            }
            if (totalPageNumber <= pageNumber) {
                pageNumber = totalPageNumber - 1;
            }

            List<OtcTransactionPendOrderDO> otcTransactionPendOrderList = null;
            if (totalNumber > 0) {
                otcTransactionPendOrderList = otcTransactionPendOrderService.listOtcTransactionPendOrder(user.getUserId(), pageNumber, pageSize);
            }

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("totalNumber", totalNumber);
            jsonObject.put("totalPageNumber", totalPageNumber);
            jsonObject.put("pageNumber", pageNumber);
            jsonObject.put("otcTransactionPendOrderList", otcTransactionPendOrderList);
            resultJson.setData(jsonObject);
            resultJson.setCode(1);
            resultJson.setMessage("查询成功");
        }

        return resultJson;
    }

    /**
     * 进入经销商发起广告
     */
    @RequestMapping("/openInitiateAds.htm")
    public String openInitiateAds(HttpServletRequest request){
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/wap/login";
        }

        return "page/wap/openInitiateAds";
    }

    /**
     * OTC 场外交易挂单 经销商发布广告（用户出售）
     */
    @RequestMapping(value = "/initiateAds.htm", method = RequestMethod.POST)
    public  @ResponseBody JsonObjectBO initiateAds(HttpServletRequest request) {
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            resultJson.setCode(4);
            resultJson.setMessage("未登录！");
            return resultJson;
        }
        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            resultJson.setCode(3);
            resultJson.setMessage("用户操作频繁");
            return resultJson;
        }
        //判断用户是否为经销商
        if(userSession.getIsDealer() != 2){//不是经销商
            resultJson.setCode(3);
            resultJson.setMessage("当前用户不是经销商");
            return resultJson;
        }

        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyId"));
        String orderTypeStr = StringUtil.stringNullHandle(request.getParameter("orderType"));
        String pendingRatioStr = StringUtil.stringNullHandle(request.getParameter("pendingRatio"));
        String maxNumberStr = StringUtil.stringNullHandle(request.getParameter("maxNumber"));
        String minNumberStr = StringUtil.stringNullHandle(request.getParameter("minNumber"));
        String ara = StringUtil.stringNullHandle(request.getParameter("ara"));
        if(!StringUtil.isNotNull(currencyIdStr) || !StringUtil.isNotNull(orderTypeStr) || !StringUtil.isNotNull(pendingRatioStr)
                || !StringUtil.isNotNull(maxNumberStr) || !StringUtil.isNotNull(minNumberStr)){
            resultJson.setCode(2);
            resultJson.setMessage("参数错误");
            return resultJson;
        }

        int currencyId = 0;
        int orderType = 0;
        double pendingRatio = 0;
        double maxNumber = 0;
        double minNumber = 0;
        if (StringUtil.isNotNull(currencyIdStr)) {
            currencyId = Integer.parseInt(currencyIdStr);
        }
        if (StringUtil.isNotNull(orderTypeStr)) {
            orderType = Integer.parseInt(orderTypeStr);
        }
        if (StringUtil.isNotNull(pendingRatioStr)) {
            pendingRatio = Double.parseDouble(pendingRatioStr);
        }
        if (StringUtil.isNotNull(maxNumberStr)) {
            maxNumber = Double.parseDouble(maxNumberStr);
        }
        if (StringUtil.isNotNull(minNumberStr)) {
            minNumber = Double.parseDouble(minNumberStr);
        }

        OtcTransactionPendOrderDTO otcOrderVO = new OtcTransactionPendOrderDTO();
        otcOrderVO.setUserId(userSession.getUserId());
        otcOrderVO.setUserAccount(userSession.getUserAccount());
        if(minNumber < 0){
            resultJson.setCode(3);
            resultJson.setMessage("最低限额不能小于0");
            return resultJson;
        }
        if(maxNumber > 999999.99){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额要小于一百万");
            return resultJson;
        }
        if(otcOrderVO.getPendingRatio() > 999999.99){
            resultJson.setCode(3);
            resultJson.setMessage("挂单比例要小于一百万");
            return resultJson;
        }
        if(BigDecimalUtil.mul( BigDecimalUtil.mul(pendingRatio,0.0001),10000)>BigDecimalUtil.mul(maxNumber,10000)){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额过小");
            return resultJson;
        }
        if(maxNumber <= minNumber){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额要大于最低限额");
            return resultJson;
        }
        if(orderType !=1 && orderType !=2){
            resultJson.setCode(3);
            resultJson.setMessage("订单类型不正确");
            return resultJson;
        }
        otcOrderVO.setMaxNumber(maxNumber);
        otcOrderVO.setMinNumber(minNumber);
        otcOrderVO.setCurrencyId(currencyId);
        otcOrderVO.setPendingRatio(pendingRatio);
        otcOrderVO.setOrderType(orderType);

        if(currencyId == 999) {//如果是999  即为xt 币
            otcOrderVO.setCurrencyName("XT");
        }
        if(orderType == 1){
            String selects = StringUtil.stringNullHandle(request.getParameter("selectsList"));

            String[] selectsStr = selects.split(",");
            List<String> selectsList = new ArrayList<String>();
            for(String select : selectsStr){
                if(!StringUtil.isNotNull(select)){
                    continue;
                }
                selectsList.add(select);
            }
            if(selectsList == null || selectsList.size() == 0){
                resultJson.setCode(1);
                resultJson.setMessage("请选择收款方式");
                return resultJson;
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("otcOrderVO", otcOrderVO);
            jsonObject.put("selectsList", selectsList);
            resultJson.setData(jsonObject);
            resultJson.setCode(1);
            resultJson.setMessage("跳转收款信息");
            return resultJson;

        }
        // 根据挂单类型判断 出售单：1 回购单：2   出售单 判断币种信息   回购单不做判断
        if(currencyId != 999){//如果是xt 币    不用判断币种信息  直接允许挂单
            if (orderType == 2) {
                //获取币种信息
                TransactionCurrencyDO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyId(otcOrderVO.getCurrencyId());
                otcOrderVO.setCurrencyName(transactionCurrency.getCurrencyName());
                if (transactionCurrency == null) {
                    resultJson.setCode(3);
                    resultJson.setMessage("币种信息获取失败,请稍候再试");
                    return resultJson;
                }

                if (transactionCurrency.getUpStatus() == 4) {
                    resultJson.setCode(5);
                    resultJson.setMessage("该币种已下线");
                    return resultJson;
                }

                if (transactionCurrency.getPaymentType() != 1) {
                    resultJson.setCode(4);
                    resultJson.setMessage("该币种不在交易状态");
                    return resultJson;
                }
                //获取经销商币信息
                UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(userSession.getUserId(),
                        otcOrderVO.getCurrencyId());
                if (userCurrencyNum == null) {
                    UserCurrencyNumDO userCurrencyNumDO = new UserCurrencyNumDO();
                    userCurrencyNumDO.setUserId(userSession.getUserId());
                    userCurrencyNumDO.setCurrencyId(otcOrderVO.getCurrencyId());
                    userCurrencyNumDO.setCurrencyNumber(0);
                    userCurrencyNumDO.setCurrencyNumberLock(0);
                    userCurrencyNumDO.setAddTime(DateUtil.getCurrentTime());

                    userCurrencyNumService.insertUserCurrencyNum(userCurrencyNumDO);
                    resultJson.setCode(5);
                    resultJson.setMessage("经销商币不足");
                    return resultJson;
                }

                if (userCurrencyNum.getCurrencyNumber() <= 0) {
                    resultJson.setCode(5);
                    resultJson.setMessage("经销商币不足");
                    return resultJson;
                }
            }
        }
        //挂单操作
        resultJson = otcTransactionPendOrderService.insertPendOrder(otcOrderVO);
        return resultJson;
    }

    /**
     * 进入经销商收款
     */
    @RequestMapping("/otcReceipt.htm")
    public String otcReceipt(HttpServletRequest request){
        UserSessionBO user = UserWapInterceptor.getUser(request);
        if (user == null) {
            request.setAttribute("code", 4);
            request.setAttribute("message", "未登录！");
            return "page/wap/login";
        }
        String currencyIdStr = StringUtil.stringNullHandle(request.getParameter("currencyIds"));
        String orderTypeStr = StringUtil.stringNullHandle(request.getParameter("orderTypes"));
        String pendingRatioStr = StringUtil.stringNullHandle(request.getParameter("pendingRatios"));
        String maxNumberStr = StringUtil.stringNullHandle(request.getParameter("maxNumbers"));
        String minNumberStr = StringUtil.stringNullHandle(request.getParameter("minNumbers"));
        String ara = StringUtil.stringNullHandle(request.getParameter("aras"));
        String selectList = StringUtil.stringNullHandle(request.getParameter("selectList"));

        request.setAttribute("currencyId", currencyIdStr);
        request.setAttribute("orderType", orderTypeStr);
        request.setAttribute("pendingRatio", pendingRatioStr);
        request.setAttribute("maxNumber", maxNumberStr);
        request.setAttribute("minNumber", minNumberStr);
        request.setAttribute("selectList", selectList);
        request.setAttribute("ara", ara);

        return "page/wap/otcReceipt";
    }

    /**
     * OTC 场外交易挂单 经销商发布订单填写收款信息 （用户购买）
     */
    @RequestMapping(value = "/otcRelease.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO otcRelease(HttpServletRequest request, OtcTransactionPendOrderDTO otcOrderVO, MultipartFile alipayImageUrl, MultipartFile wechatImageUrl) {
        JsonObjectBO resultJson = new JsonObjectBO();
        UserSessionBO userSession = UserWebInterceptor.getUser(request);
        if (userSession == null) {
            resultJson.setCode(4);
            resultJson.setMessage("未登录");
            return resultJson;
        }
        boolean fq =UserWebInterceptor.handleFrequent(request);
        if(fq){
            resultJson.setCode(2);
            resultJson.setMessage("用户操作频繁");
            return resultJson;
        }
        //判断用户是否为经销商
        if(userSession.getIsDealer() != 2){//不是经销商
            resultJson.setCode(3);
            resultJson.setMessage("当前用户不是经销商");
            return resultJson;
        }
        otcOrderVO.setUserId(userSession.getUserId());
        otcOrderVO.setUserAccount(userSession.getUserAccount());
        // 校验参数
        if (otcOrderVO.getCurrencyId() == 0 || otcOrderVO.getOrderType() == 0 || otcOrderVO.getPendingRatio() == 0 || otcOrderVO.getMaxNumber() <= 0 || otcOrderVO.getMaxNumber() <= otcOrderVO.getMinNumber()) {
            resultJson.setCode(3);
            resultJson.setMessage("参数错误");
            return resultJson;
        }
        if(otcOrderVO.getMinNumber() < 0){
            resultJson.setCode(3);
            resultJson.setMessage("最低限额不能小于0");
            return resultJson;
        }
        if(otcOrderVO.getMaxNumber() > 999999.99){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额要小于一百万");
            return resultJson;
        }
        if(otcOrderVO.getPendingRatio() > 999999.99){
            resultJson.setCode(3);
            resultJson.setMessage("挂单比例要小于一百万");
            return resultJson;
        }
        if(BigDecimalUtil.mul( BigDecimalUtil.mul(otcOrderVO.getPendingRatio(),0.0001),10000)>BigDecimalUtil.mul(otcOrderVO.getMaxNumber(),10000)){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额过小");
            return resultJson;
        }
        if(otcOrderVO.getMaxNumber() <= otcOrderVO.getMinNumber()){
            resultJson.setCode(3);
            resultJson.setMessage("最高限额要大于最低限额");
            return resultJson;
        }
        if(otcOrderVO.getOrderType() !=1 && otcOrderVO.getOrderType() !=2){
            resultJson.setCode(3);
            resultJson.setMessage("订单类型不正确");
            return resultJson;
        }
        if (otcOrderVO.getOrderType() == 1) {//出售单需要判断 回购单不需要判断支付方式
            // 判断是否选择付款方式
            if (!StringUtil.isNotNull(otcOrderVO.getBankAccount()) && !StringUtil.isNotNull(otcOrderVO.getAlipayAccount()) && !StringUtil.isNotNull(otcOrderVO.getWechatAccount())) {
                resultJson.setCode(3);
                resultJson.setMessage("付款方式参数错误");
                return resultJson;
            }
            //根据不同支付方式校验 不同的 必填参数
            if (StringUtil.isNotNull(otcOrderVO.getBankAccount())) {//银行卡
                if (!StringUtil.isNotNull(otcOrderVO.getBankAccount()) || !StringUtil.isNotNull(otcOrderVO.getBankName()) || !StringUtil.isNotNull(otcOrderVO.getBankBranch())
                        || !StringUtil.isNotNull(otcOrderVO.getPaymentName()) || !StringUtil.isNotNull(otcOrderVO.getPaymentPhone())) {
                    resultJson.setCode(3);
                    resultJson.setMessage("银行参数错误");
                    return resultJson;
                }
            }
            if (StringUtil.isNotNull(otcOrderVO.getAlipayAccount())) {//支付宝
                if (alipayImageUrl == null || alipayImageUrl.isEmpty()) {
                    resultJson.setCode(3);
                    resultJson.setMessage("支付宝参数错误");
                    return resultJson;
                }
                String alipayImage = ImageReduceUtil.reduceImageUploadRemote(alipayImageUrl, FileUrlConfig.file_remote_qeCodeImage_url);
                if (alipayImage.equals("") || alipayImage == null) {
                    resultJson.setCode(3);
                    resultJson.setMessage("微信二维码上传失败");
                    return resultJson;
                }
                otcOrderVO.setAlipayImage(alipayImage);
            }
            if (StringUtil.isNotNull(otcOrderVO.getWechatAccount())) {//微信
                if (wechatImageUrl == null || wechatImageUrl.isEmpty()) {
                    resultJson.setCode(3);
                    resultJson.setMessage("微信参数错误");
                    return resultJson;
                }
                String wechatImage = ImageReduceUtil.reduceImageUploadRemote(wechatImageUrl, FileUrlConfig.file_remote_qeCodeImage_url);
                if (wechatImage.equals("") || wechatImage == null) {
                    resultJson.setCode(3);
                    resultJson.setMessage("微信二维码上传失败");
                    return resultJson;
                }
                otcOrderVO.setWechatImage(wechatImage);
            }
        }
        if(otcOrderVO.getCurrencyId() == 999) {//如果是999  即为xt 币
            otcOrderVO.setCurrencyName("XT");
        }
        //挂单操作
        resultJson = otcTransactionPendOrderService.insertPendOrder(otcOrderVO);

        return resultJson;
    }

    /**
     * 根据订单号删除用户挂单信息
     */
    @RequestMapping(value = "/deleteOtcTransactionPendOrder.htm", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO deleteOtcTransactionPendOrder(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();

        UserSessionBO user = UserWebInterceptor.getUser(request);
        if (user == null) {
            responseJson.setCode(2);
            responseJson.setMessage("未登录");
            return responseJson;
        }

        String otcPendingOrderNo = StringUtil.stringNullHandle(request.getParameter("otcPendingOrderNo"));
        if (!StringUtil.isNotNull(otcPendingOrderNo)) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误");
            return responseJson;
        }

        Timestamp currentTime = DateUtil.getCurrentTime();
        boolean deleteOtcTransaction = otcTransactionPendOrderService.deleteOtcTransactionPendOrderByOtcPendingOrderNo(user.getUserId(), otcPendingOrderNo, currentTime);
        if(!deleteOtcTransaction){
            responseJson.setCode(3);
            responseJson.setMessage("删除失败");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("删除成功");
        return responseJson;
    }

}
