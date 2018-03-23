package com.jydp.controller.wap;

import com.iqmkj.utils.StringUtil;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.service.IHomePageService;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/userWap/homePage")
@Scope(value = "prototype")
public class WapHomePageController {


    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /**web端首页*/
    @Autowired
    private IHomePageService homePageService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 展示index页面 */
    @RequestMapping(value = "/show")
    public String showIndex(HttpServletRequest request) {

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
            Object systemNoticListObject = redisService.getValue(RedisKeyConfig.HOMEPAGE_NOTICE);
            if (systemNoticListObject != null) {
                systemNoticeList = (List<SystemNoticeDO>)systemNoticListObject;
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
                redisService.addValue(RedisKeyConfig.HOMEPAGE_NOTICE,systemNoticeList);
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

            request.setAttribute("systemAdsHomepagesList",systemAdsHomepagesList);
            request.setAttribute("systemNoticeList",systemNoticeList);
            request.setAttribute("systemBusinessesPartnerList",systemBusinessesPartnerList);
            request.setAttribute("transactionUserDealList",transactionUserDealList);

            return "page/wap/index";
        }



}
