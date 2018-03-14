package com.jydp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

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

    /**web端首页*/
    @Autowired
    private IHomePageService homePageService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 跳转至首页 */
    @RequestMapping("/show")
    public String getHomePageData(HttpServletRequest request){

        //查询首页广告列表
        List<SystemAdsHomepagesDO> systemAdsHomepagesDOList = (List<SystemAdsHomepagesDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_ADS);

        if (systemAdsHomepagesDOList != null && systemAdsHomepagesDOList.size() > 0) {
            for (SystemAdsHomepagesDO systemAdsHomepagesDO : systemAdsHomepagesDOList) {
                String webLinkUrl = systemAdsHomepagesDO.getWebLinkUrl();
                if (StringUtil.isNotNull(webLinkUrl)) {
                    webLinkUrl = HtmlUtils.htmlEscape(webLinkUrl);
                    systemAdsHomepagesDO.setWebLinkUrl(webLinkUrl);
                }
            }
        }


        //查询所有币行情信息
        List<TransactionUserDealDTO> transactionUserDealDTOList = (List<TransactionUserDealDTO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_CURRENCYMARKET);

        //查询系统公告列表
        List<SystemNoticeDO> systemNoticeDOList = (List<SystemNoticeDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_NOTICE);

        if (systemNoticeDOList != null && systemNoticeDOList.size() > 0) {
            for (SystemNoticeDO systemNotice : systemNoticeDOList) {
                String noticeTitle = systemNotice.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemNotice.setNoticeTitle(noticeTitle);
                }
            }
        }

        //查询热门话题列表
        List<SystemHotDO> systemHotDOList = (List<SystemHotDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_HOTTOPIC);

        if (systemHotDOList != null && systemHotDOList.size() > 0) {
            for (SystemHotDO systemHot : systemHotDOList) {
                String noticeTitle = systemHot.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemHot.setNoticeTitle(noticeTitle);
                }
            }
        }

        //查询合作商家列表
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerDOList = (List<SystemBusinessesPartnerDO>)redisService.getValue(RedisKeyConfig.HOMEPAGE_PARTNER);

        if (systemBusinessesPartnerDOList != null && systemBusinessesPartnerDOList.size() > 0) {
            for (SystemBusinessesPartnerDO systemBusinessesPartnerDO : systemBusinessesPartnerDOList) {
                String webLinkUrl = systemBusinessesPartnerDO.getWebLinkUrl();
                if (StringUtil.isNotNull(webLinkUrl)) {
                    webLinkUrl = HtmlUtils.htmlEscape(webLinkUrl);
                    systemBusinessesPartnerDO.setWebLinkUrl(webLinkUrl);
                }
            }
        }

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

    /** 获取所有币种信息(menu.jsp交易中心选项卡使用) */
    @RequestMapping("/getAllCurrency")
    public @ResponseBody JsonObjectBO getAllCurrency(){

        JsonObjectBO jsonObjectBO = new JsonObjectBO();

        List<TransactionCurrencyVO> transactionCurrencyVOList = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWeb();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionCurrencyList",transactionCurrencyVOList);

        jsonObjectBO.setCode(1);
        jsonObjectBO.setMessage("查询成功");
        jsonObjectBO.setData(jsonObject);
        return jsonObjectBO;
    }
}
