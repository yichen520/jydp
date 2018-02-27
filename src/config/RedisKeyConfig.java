package config;

/**
 * redisKey前缀配置
 * @author whx
 */
public class RedisKeyConfig {

    /** 买一价key */
    public static final String buyOneKey = "buyOne";
    /** 卖一价key */
    public static final String sellOneKey = "sellOne";
    /** 买入挂单记录key */
    public static final String buyKey = "transactionPendOrderBuyList";
    /** 卖出挂单记录key */
    public static final String sellKey = "transactionPendOrderSellList";

}
