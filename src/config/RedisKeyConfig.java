package config;

/**
 * redisKey前缀配置
 * @author whx
 */
public class RedisKeyConfig {

    /** 凌晨至开盘时间 */
    public static final long OPENING_TIME  = 1L * 8 * 60 * 60 * 1000;
    /** 一天的总毫秒数 */
    public static final long DAY_TIME  = 1L * 24 * 60 * 60 * 1000;
    /** 当前交易价格 */
    public static final String NOW_PRICE = "nowPrice_";
    /** 买一价key */
    public static final String BUY_ONE_KEY = "buyOne";
    /** 卖一价key */
    public static final String SELL_ONE_KEY = "sellOne";
    /** 今日最高价 */
    public static final String TODAY_MAX_PRICE = "todayMax_";
    /** 今日最低价 */
    public static final String TODAY_MIN_PRICE = "todayMin_";
    /** 今日涨幅 */
    public static final String TODAY_RANGE = "todayRange_";
    /** 24小时成交量 */
    public static final String DAY_TURNOVER = "dayTurnove_";
    /** 24小时成交额 */
    public static final String DAY_VOLUME_OF_TRANSACTION = "dayTransaction_";
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
    /** 币种成交记录key（后面需跟上币种简称） */
    public static final String CURRENCY_DEAL_KEY = "transactionCurrencyDealList_";
}
