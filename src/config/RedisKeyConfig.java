package config;

/**
 * redisKey前缀配置
 * @author whx
 */
public class RedisKeyConfig {

    /** 服务器前缀 */
    public static final String PERFIX = "EXT:";

    /** 凌晨至开盘时间 */
    public static final long OPENING_TIME  = 1L * 8 * 60 * 60 * 1000;
    /** 一天的总毫秒数 */
    public static final long DAY_TIME  = 1L * 24 * 60 * 60 * 1000;
    /** 当前交易价格 */
    public static final String NOW_PRICE = PERFIX + "nowPrice:";
    /** 买一价key（后面需跟上币种id） */
    public static final String BUY_ONE_KEY = PERFIX + "buyOne:";
    /** 卖一价key（后面需跟上币种id） */
    public static final String SELL_ONE_KEY = PERFIX + "sellOne:";
    /** 今日最高价（后面需跟上币种id） */
    public static final String TODAY_MAX_PRICE = PERFIX + "todayMax:";
    /** 今日最低价（后面需跟上币种id） */
    public static final String TODAY_MIN_PRICE = PERFIX + "todayMin:";
    /** 今日涨幅（后面需跟上币种id） */
    public static final String TODAY_RANGE = PERFIX + "todayRange:";
    /** 昨日收盘价（后面需跟上币种id） */
    public static final String YESTERDAY_PRICE  = PERFIX + "yesterdayPrice:";
    /** 今日小时成交量（后面需跟上币种id） */
    public static final String DAY_TURNOVER = PERFIX + "dayTurnove:";
    /** 今日成交额（后面需跟上币种id） */
    public static final String DAY_VOLUME_OF_TRANSACTION = PERFIX + "dayTransaction:";
    /** 买入挂单记录key（后面需跟上币种id） */
    public static final String BUY_KEY = PERFIX + "transactionPendOrderBuyList:";
    /** 币种成交记录key（后面需跟上币种id） */
    public static final String CURRENCY_DEAL_KEY = PERFIX + "transactionCurrencyDealList:";
    /** 卖出挂单记录key（后面需跟上币种id） */
    public static final String SELL_KEY = PERFIX + "transactionPendOrderSellList:";
    /** k线图展示参数key（后面需跟上币种id） */
    public static final String GRAPH_DATA = PERFIX + "graphData:";
    /** 首页广告列表 */
    public static final String HOMEPAGE_ADS = PERFIX + "homepageAdvertisementList:";
    /** 首页系统公告列表 */
    public static final String HOMEPAGE_NOTICE = PERFIX + "homepageSystemNoticeList:";
    /** 首页热门话题列表 */
    public static final String HOMEPAGE_HOT_TOPIC = PERFIX + "homepageHotTopicList:";
    /** 首页合作商家列表 */
    public static final String HOMEPAGE_PARTNER = PERFIX + "homepageBusinessesPartnerList:";
    /** 首页币种行情列表 */
    public static final String HOMEPAGE_CURRENCY_MARKET = PERFIX + "homepageCurrencyMarketList:";
}
