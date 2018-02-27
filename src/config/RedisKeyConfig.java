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

}
