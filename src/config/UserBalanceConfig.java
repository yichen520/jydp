package config;

/**
 * 用户账户记录配置
 * @author hz
 */
public class UserBalanceConfig {

    /** XT统一 */

    /** 币种名称 */
    public static final String DOLLAR = "XT";
    /** 币种id */
    public static final int DOLLAR_ID = 999;

    /** 记录来源 */

    /** 撤销买入挂单 */
    public static final String REVOKE_BUY_ORDER = "撤销挂单返还冻结XT";
    /** 撤销卖出挂单 */
    public static final String REVOKE_SELL_ORDER = "撤销挂单返还冻结币";
    /** 系统操作 */
    public static final String SYSTEM_OPERATE = "系统操作";
    /** 挂单成交 */
    public static final String PEND_SUCCESS = "挂单成交";
    /** 买入挂单 */
    public static final String BUY_ENTRUST = "买入挂单";
    /** 卖出挂单 */
    public static final String SELL_ENTRUST = "卖出挂单";
    /** 币种提现 */
    public static final String COIN_WITHDRAWAL = "币种提现";
    /** 买入线下广告 */
    public static final String BUY_OFFLINE_AD = "线下买入广告";
    /** 卖出线下广告 */
    public static final String SELL_OFFLINE_AD = "线下卖出广告";


}
