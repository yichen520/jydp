package com.jydp.controller.userWeb;

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
 * @author xzp
 */
@Controller
@RequestMapping("/web/homePage")
@Scope(value = "prototype")
public class WebHomePageController {

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
    public @ResponseBody JsonObjectBO getHomePageData(HttpServletRequest request){
        String path = request.getServerName();
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        //查询首页广告列表
        List<SystemAdsHomepagesDO> systemAdsHomepagesList = null;
        Object systemAdsHomepagesListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_ADS);
        if (systemAdsHomepagesListObject != null) {
            systemAdsHomepagesList = (List<SystemAdsHomepagesDO>)systemAdsHomepagesListObject;
        }

        if (systemAdsHomepagesList != null && systemAdsHomepagesList.size() > 0) {
            for (SystemAdsHomepagesDO systemAdsHomepages : systemAdsHomepagesList) {
                String webLinkUrl = systemAdsHomepages.getWebLinkUrl();
                if (StringUtil.isNotNull(webLinkUrl)) {
                    webLinkUrl = HtmlUtils.htmlEscape(webLinkUrl);
                    systemAdsHomepages.setWebLinkUrl(webLinkUrl);
                }
            }
        } else {
            //redis中没有，从数据库中查询并添加至redis中
            systemAdsHomepagesList = homePageService.getSystemAdsHomepageList();
            redisService.addValue(RedisKeyConfig.HOMEPAGE_ADS,systemAdsHomepagesList);
        }

        //查询所有币行情信息
        List<TransactionUserDealDTO> transactionUserDealList = null;
        Object transactionUserDealListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_CURRENCY_MARKET);
        if (transactionUserDealListObject != null) {
            transactionUserDealList = (List<TransactionUserDealDTO>)transactionUserDealListObject;
        }

        //查询系统公告列表
        List<SystemNoticeDO> systemNoticeList = null;
        Object systemNoticListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_NOTICE);;
        if (systemNoticListObject != null ) {
            if (((List<SystemNoticeDO>)systemNoticListObject).size()>4) {
                systemNoticeList = ((List<SystemNoticeDO>)systemNoticListObject).subList(0, 4);
            } else {
                systemNoticeList = ((List<SystemNoticeDO>)systemNoticListObject);
            }
        }

        if (systemNoticeList != null && systemNoticeList.size() > 0) {
            for (SystemNoticeDO systemNotice : systemNoticeList) {
                String noticeTitle = systemNotice.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemNotice.setNoticeTitle(noticeTitle);
                }
            }
        } else {
            //redis中没有，从数据库中查询并添加至redis中
            systemNoticeList = homePageService.getSystemNoticeList();
            if (systemNoticeList.size()>4) {
                systemNoticeList=systemNoticeList.subList(0,4);
            }
            redisService.addValue(RedisKeyConfig.HOMEPAGE_NOTICE,systemNoticeList);
        }

        //查询热门话题列表
        List<SystemHotDO> systemHotList = null;
        Object systemHotListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_HOT_TOPIC);
        if (systemHotListObject != null) {
            if (((List<SystemNoticeDO>)systemNoticListObject).size()>4) {
                systemNoticeList = ((List<SystemNoticeDO>)systemNoticListObject).subList(0, 4);
            } else {
                systemNoticeList = ((List<SystemNoticeDO>)systemNoticListObject);
            }
        }

        if (systemHotList != null && systemHotList.size() > 0) {
            for (SystemHotDO systemHot : systemHotList) {
                String noticeTitle = systemHot.getNoticeTitle();
                if (StringUtil.isNotNull(noticeTitle)) {
                    noticeTitle = HtmlUtils.htmlEscape(noticeTitle);
                    systemHot.setNoticeTitle(noticeTitle);
                }
            }
        } else {
            //redis中没有，从数据库中查询并添加至redis中
            systemNoticeList = homePageService.getSystemNoticeList();
            if (systemNoticeList.size()>4) {
                systemNoticeList=systemNoticeList.subList(0,4);
            }
            redisService.addValue(RedisKeyConfig.HOMEPAGE_HOT_TOPIC,systemHotList);
        }

        //查询合作商家列表
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerList = null;
        Object systemBusinessesPartnerListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_PARTNER);
        if (systemBusinessesPartnerListObject != null) {
            systemBusinessesPartnerList = (List<SystemBusinessesPartnerDO>)systemBusinessesPartnerListObject;
        }

        if (systemBusinessesPartnerList != null && systemBusinessesPartnerList.size() > 0) {
            for (SystemBusinessesPartnerDO systemBusinessesPartner : systemBusinessesPartnerList) {
                String webLinkUrl = systemBusinessesPartner.getWebLinkUrl();
                if (StringUtil.isNotNull(webLinkUrl)) {
                    webLinkUrl = HtmlUtils.htmlEscape(webLinkUrl);
                    systemBusinessesPartner.setWebLinkUrl(webLinkUrl);
                }
            }
        } else {
            //redis中没有，从数据库中查询并添加至redis中
            systemBusinessesPartnerList = homePageService.getSystemBusinessesPartnerList();
            redisService.addValue(RedisKeyConfig.HOMEPAGE_PARTNER,systemBusinessesPartnerList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path",path);
        jsonObject.put("systemAdsHomepagesList",systemAdsHomepagesList);
        jsonObject.put("systemNoticeList",systemNoticeList);
        jsonObject.put("systemHotList",systemHotList);
        jsonObject.put("systemBusinessesPartnerList",systemBusinessesPartnerList);
        jsonObject.put("transactionUserDealList",transactionUserDealList);
        jsonObjectBO.setCode(300000);
        jsonObjectBO.setMessage("查询成功");
        jsonObjectBO.setData(jsonObject);
        return jsonObjectBO;
    }

    /** 获取币种行情信息 */
    @RequestMapping("/getCurrencyMarket")
    public  @ResponseBody JsonObjectBO getCurrencyMarket(){

        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        //查询所有币行情信息
        List<TransactionUserDealDTO> transactionUserDealList = null;
        Object transactionUserDealListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_CURRENCY_MARKET);
        if (transactionUserDealListObject != null) {
            transactionUserDealList = (List<TransactionUserDealDTO>)transactionUserDealListObject;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionUserDealList",transactionUserDealList);
        jsonObjectBO.setCode(1);
        jsonObjectBO.setMessage("查询成功");
        jsonObjectBO.setData(jsonObject);
        return jsonObjectBO;
    }

    /** 获取所有币种信息(menu.jsp交易中心选项卡使用) */
    @RequestMapping("/getAllCurrency")
    public @ResponseBody JsonObjectBO getAllCurrency(){

        JsonObjectBO jsonObjectBO = new JsonObjectBO();

        List<TransactionCurrencyVO> transactionCurrencyList = transactionCurrencyService.getOnlineAndSuspensionCurrencyForWeb();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transactionCurrencyList",transactionCurrencyList);

        jsonObjectBO.setCode(1);
        jsonObjectBO.setMessage("查询成功");
        jsonObjectBO.setData(jsonObject);
        return jsonObjectBO;
    }
}
