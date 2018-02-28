package config;

/**
 * redisKey前缀配置
 * @author whx
 */
public class RedisKeyConfig {

    /** 买一价key */
    public static final String BUY_ONE_KEY = "buyOne";
    /** 卖一价key */
    public static final String SELL_ONE_KEY = "sellOne";
    /** 买入挂单记录key（后面需跟上币种id） */
    public static final String BUY_KEY = "transactionPendOrderBuyList";
    /** 卖出挂单记录key（后面需跟上币种id） */
    public static final String SELL_KEY = "transactionPendOrderSellList";
    /** 首页广告列表 */
    public static final String HOMEPAGE_ADS = "homepgeAdvertisementList";
    /** 首页系统公告列表 */
    public static final String HOMEPAGE_NOTICE = "homepageSystemNoticeList";
    /** 首页热门话题列表 */
    public static final String HOMEPAGE_HOTTOPIC = "homepageHotTopicList";
    /** 首页合作商家列表 */
    public static final String HOMEPAGE_PARTNER = "homepageBusinessesPartnerList";
    /** 首页币种行情列表 */
    public static final String HOMEPAGE_CURRENCYMARKET = "homepageCurrencyMarketList";

}
