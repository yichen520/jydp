package config;

/**
 * 系统返回消息类型和提示配置类
 * @author njx
 **/
public class SystemMessageConfig {
    /**
     * 用户以及用户状态相关提示
     */
    //用户未登录
    public static final int NOT_LOGININ_CODE = 4;
    public static final String NOT_LOGININ_MESSAGE = "未登录";
    //用户不存在
    public static final int USER_NOT_EXIST_CODE = 3;
    public static final String USER_NOT_EXIST_MESSAGE = "该用户不存在";
    //用户账号被禁用
    public static final int ACCOUNT_DISABLED_CODE = 6;
    public static final String ACCOUNT_DISABLED_MESSAGE = "该账号已被禁用";
    //余额不足
    public static final int MONEY_NOT_ENOUGH_CODE = 13;
    public static final String MONEY_NOT_ENOUGH_MESSAGE = "用户余额不足";

    /**
     * 币种和交易相关
     */
    //全部币种没有上线
    public static final int CURRENCY_NOTONLINE_CODE = 5;
    public static final String CURRENCYNOTONLINE_MESSAGE = "没有上线币种";
    //币种信息不存在
    public static final int NOT_HAVE_CURRENCY_INFO_CODE = 16;
    public static final String NOT_HAVE_CURRENCY_INFO_MESSAGE = "币种信息获取失败,请稍候再试";
    //币种已下线
    public static final int CURRENCY_OFFLINE_CODE = 7;
    public static final String CURRENCY_OFFLINE_MESSAGE = "该币种已下线";
    //币种不在交易状态
    public static final int CURRENCY_NOT_TRADED_CODE = 8;
    public static final String CURRENCY_NOT_TRADED_MESSAGE = "该币种不在交易状态";
    //不在交易时间
    public static final int NOT_IN_TRADED_TIME_CODE = 9;
    public static final String NOT_IN_TRADED_TIME_MESSAGE = "不在交易时间段内";
    //交易数量
    public static final int TRADE_NUM_WRONG_CODE = 10;
    public static final String TRADE_NUM_WRONG_MESSAGE = "交易数量不能小于等于0";
    //交易价格
    public static final int TRADE_PRICE_WRONG_CODE = 11;
    public static final String TRADE_PRICE_WRONG_MESSAGE = "交易单价不能小于等于0";
    //支付密码错误
    public static final int TRADE_PASS_WRONG_CODE = 12;
    public static final String TRADE_PASS_WRONG_MESSAGE = "支付密码错误";
    //挂单失败
    public static final int PEND_FAILURE_CODE = 14;
    public static final String PEND_FAILURE_MESSAGE = "挂单失败";
    //挂单成功
    public static final int PEND_SUCCESS_CODE = 15;
    public static final String PEND_SUCCESS_MESSAGE = "挂单成功";
    //币不足
    public static final int COIN_NOT_ENOUGH_CODE = 17;
    public static final String COIN_NOT_ENOUGH_MESSAGE = "用户币不足";

    /**
     * 操作错误
     */
    public static final int OPERATING_FREQUENCY_CODE = 2;
    public static final String OPERATING_FREQUENCY_MESSAGE = "用户操作频繁";
    //修改成功
    public static final int MODIFY_SUCCESS_CODE = 18;
    public static final String MODIFY_SUCCESS_MESSAGE = "修改成功";
    //修改失败
    public static final int MODIFY_FAILD_CODE = 19;
    public static final String MODIFY_FAILD_MESSAGE = "修改失败";

    /**
     * 重定向消息
     */
    public static final int REDIRECT_TO_HOMEPAGE_CODE = 302;
    public static final String REDIRECT_TO_HOMEPAGE_MESSAGE = "/web/homePage/show";

    /**
     * 系统消息
     */
    //参数错误
    public static final int PARAMETER_ERROR_CODE = -1;
    public static final String PARAMETER_ERROR_MESSAGE = "参数错误";
    //返回成功
    public static final int SUCCESS_OPT_CODE = 0;
    public static final String SUCCESS_OPT_MESSAGE = "查询成功";
}
