package com.jydp.service.impl.system;

import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * web端首页
 * @author yk
 */
@Service("homePageService")
public class HomePageServiceImpl implements IHomePageService{

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 系统公告 */
    @Autowired
    private ISystemNoticeService systemNoticeService;

    /** 首页广告 */
    @Autowired
    private ISystemAdsHomepagesService systemAdsHomepagesService;

    /** 热门话题 */
    @Autowired
    private ISystemHotService systemHotService;

    /** 合作商家 */
    @Autowired
    private ISystemBusinessesPartnerService systemBusinessesPartnerService;

    /**
     * 查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    @Override
    public List<SystemNoticeDO> getSystemNoticeList(){
        List<SystemNoticeDO> systemNoticeDOList = systemNoticeService.getSystemNoticlistForWeb();
        return systemNoticeDOList;
    }

    /**
     * 查询首页广告列表
     * @return 查询成功：返回首页广告列表；查询失败：返回null
     */
    @Override
    public List<SystemAdsHomepagesDO> getSystemAdsHomepageList(){
        List<SystemAdsHomepagesDO> systemAdsHomepagesDOList = systemAdsHomepagesService.getSystemAdsHomepageslistForWeb();
        return systemAdsHomepagesDOList;
    }

    /**
     * 查询热门话题列表
     * @return 查询成功：返回热门话题列表；查询失败：返回null
     */
    @Override
    public List<SystemHotDO> getSystemHotList(){
        List<SystemHotDO> systemHotDOList = systemHotService.getSystemHotlistForWeb();
        return systemHotDOList;
    }

    /**
     * 查询合作商家列表
     * @return 查询成功：返回合作商家列表；查询失败：返回null
     */
    @Override
    public List<SystemBusinessesPartnerDO> getSystemBusinessesPartnerList(){
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerDOList = systemBusinessesPartnerService.getSystemBusinessesPartnerForWeb();
        return systemBusinessesPartnerDOList;
    }

    /**
     * 获取所有币种行情信息
     * @return 查询成功：返回所有币种行情信息；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDTO> getTransactionCurrencyMarketList() {
        List<TransactionUserDealDTO> transactionUserDealDTOList = transactionCurrencyService.getTransactionCurrencyMarketForWeb();
        return transactionUserDealDTOList;
    }


}
