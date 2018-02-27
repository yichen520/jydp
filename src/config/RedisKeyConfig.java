package config;

/**
 * redisKey前缀配置
 * @author whx
 */
public class RedisKeyConfig {

    /** 买一价key */
    public static final String BUYONEKEY = "buyOne";
    /** 卖一价key */
    public static final String SELLONEKEY = "sellOne";
    /** 买入挂单记录key（后面需跟上币种id） */
    public static final String BUYKEY = "transactionPendOrderBuyList";
    /** 卖出挂单记录key（后面需跟上币种id） */
    public static final String SELLKEY = "transactionPendOrderSellList";

}
