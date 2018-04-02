package config;

/**
 * 核对用户XT账户，数字货币账户数据配置
 * @author whx
 */
public class CheckUserAmountConfig {

    /** XT---币种id */
    public static final int USD_CURRENCYID = UserBalanceConfig.DOLLAR_ID;
    /** 超出可用XT最大值 */
    public static final double USD_BEYOND_MAX = 0.01;
    /** 超出锁定XT最大值 */
    public static final double USD_BEYOND_LOCK_MAX = 0.01;
    /** 超出可用数字货币数量的最大值 */
    public static final double COIN_BEYOND_MAX = 0.01;
    /** 超出锁定数字货币数量的最大值 */
    public static final double COIN_BEYOND_LOCK_MAX = 0.01;

    /** 系统id */
    public static final int SYSTEM_ID = 0;
    /** 系统账号 */
    public static final String SYSTEM_ACCOUNT = "system";
    /** 系统ip地址 */
    public static final String IP_ADDRESS = "127.0.0.1";

    /** 用户账户异常时通知手机号 */
    public static final String NOTICE_PHONE_ONE = "+8615954134739";
    /** 用户账户异常时通知手机号 */
    public static final String NOTICE_PHONE_TWO = "+8617858850509";
    /** 用户XT账户异常通知内容 */
    public static final String MESSAGE_USD = "警告：盛源交易所用户XT账户异常，请尽快处理。";
    /** 用户数字货币账户异常通知内容 */
    public static final String MESSAGE_COIN= "警告：盛源交易所用户数字货币账户异常，请尽快处理。";
}
