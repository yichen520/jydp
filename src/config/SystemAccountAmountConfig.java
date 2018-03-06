package config;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统账户编码表
 * @author hz
 */
public class SystemAccountAmountConfig {

    /**
     * 账户编码表
     */
    public static final Map<Integer, String> systemAccountMap = new HashMap<Integer, String>(){
        private static final long serialVersionUID = 1L;{
            put(101010, "用户系统-用户提现手续费");

            put(111010, "交易系统-挂单手续费");
            put(111011, "交易系统-卖出币手续费");
        }
    };

    /** 用户系统-用户提现手续费 */
    public static final int USER_TAKE_CASH_FEE = 101010;

    /** 交易系统-挂单手续费 */
    public static final int PEND_FEE = 111010;
    /** 交易系统-卖出币手续费 */
    public static final int TRADE_SELL_FEE = 111011;

}
