package config;

/**
 * 用户账户记录配置
 * @author hz
 */
public class UserBalanceConfig {

    /** 美金统一 */

    /** 币种名称 */
    public static final String DOLLAR = "美金";
    /** 币种id */
    public static final int DOLLAR_ID = 999;

    /** 记录来源 */

    /** 撤销买入挂单 */
    public static final String REVOKE_BUY_ORDER = "撤销挂单返还冻结美金";
    /** 撤销卖出挂单 */
    public static final String REVOKE_SELL_ORDER = "撤销挂单返还冻结币";
    /** 系统操作 */
    public static final String SYSTEM_OPERATE = "系统操作";

}
