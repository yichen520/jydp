package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.interceptor.UserWebInterceptor;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * web端首页
 * @author yk
 */
@Controller
@RequestMapping("/userWeb/homePage")
@Scope(value = "prototype")
public class HomePageController {

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 跳转至首页 */
    @RequestMapping("/show")
    public String getHomePageData(HttpServletRequest request){

        //查询首页广告列表
        List<SystemAdsHomepagesDO> systemAdsHomepagesDOList = (List<SystemAdsHomepagesDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_ADS);

        //查询所有币行情信息
        List<TransactionUserDealDTO> transactionUserDealDTOList = (List<TransactionUserDealDTO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_CURRENCYMARKET);

        //查询系统公告列表
        List<SystemNoticeDO> systemNoticeDOList = (List<SystemNoticeDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_NOTICE);

        //查询热门话题列表
        List<SystemHotDO> systemHotDOList = (List<SystemHotDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_HOTTOPIC);

        //查询合作商家列表
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerDOList = (List<SystemBusinessesPartnerDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_PARTNER);

        request.setAttribute("systemAdsHomepagesDOList",systemAdsHomepagesDOList);
        request.setAttribute("systemNoticeDOList",systemNoticeDOList);
        request.setAttribute("systemHotDOList",systemHotDOList);
        request.setAttribute("systemBusinessesPartnerDOList",systemBusinessesPartnerDOList);
        request.setAttribute("transactionUserDealDTOList",transactionUserDealDTOList);

        return "page/web/home";
    }

    /** 获取币种行情信息 */
    @RequestMapping("/getCurrencyMarket")
    public  @ResponseBody JsonObjectBO getCurrencyMarket(){

        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        //查询所有币行情信息
        List<TransactionUserDealDTO> transactionUserDealDTOList = (List<TransactionUserDealDTO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_CURRENCYMARKET);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionUserDealDTOList",transactionUserDealDTOList);

        jsonObjectBO.setCode(1);
        jsonObjectBO.setMessage("查询成功");
        jsonObjectBO.setData(jsonObject);
        return jsonObjectBO;
    }

    /** 获取所有币种信息 */
    @RequestMapping("/getAllCurrency")
    public @ResponseBody JsonObjectBO getAllCurrency(){

        JsonObjectBO jsonObjectBO = new JsonObjectBO();

        List<TransactionCurrencyVO> transactionCurrencyVOList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionCurrencyList",transactionCurrencyVOList);

        jsonObjectBO.setCode(1);
        jsonObjectBO.setMessage("查询成功");
        jsonObjectBO.setData(jsonObject);
        return jsonObjectBO;
    }
}
