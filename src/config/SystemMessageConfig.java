package config;

/**
 * 系统返回消息类型和提示配置类
 *
 * @author njx
 **/
public class SystemMessageConfig {

    /**
     * 系统返回码
     */
    //返回成功
    public static final int SYSTEM_CODE_SUCCESS=1;
    public static final String SYSTEM_MESSAGE_SUCCESS="操作成功";
    //参数错误
    public static final int SYSTEM_CODE_PARAM_ERROR=2;
    public static final String SYSTEM_MESSAGE_PARAM_ERROR="参数错误";
    //JSON格式错误
    public static final int SYSTEM_CODE_JSON_ERROR=3;
    public static final String SYSTEM_MESSAGE_JSON_ERROR="JSON格式错误";
    //身份过期
    public static final int SYSTEM_CODE_LOGIN_EXPIRED=4;
    public static final String SYSTEM_MESSAGE_LOGIN_EXPIRED="身份过期";
    //服务器异常
    public static final int SYSTEM_CODE_SERVER_EXCEPTION=5;
    public static final String SYSTEM_MESSAGE_SERVER_EXCEPTION="服务器异常";





    /**
     * 重定向消息
     */
    //联系客服
    public static final int REDIRECT_TO_WEBCUSTOMERSERVICE_CODE = 104000;
    public static final String REDIRECT_TO_WEBCUSTOMERSERVICE_MESSAGE = "/web/webCustomerService/show";

    public static final int REDIRECT_TO_USERLOGIN_CODE = 201000;
    public static final String REDIRECT_TO_USERLOGIN_MESSAGE = "/web/userLogin/show";

    public static final int REDIRECT_TO_HOMEPAGE_CODE = 301000;
    public static final String REDIRECT_TO_HOMEPAGE_MESSAGE = "/web/homePage/show";

    //交易中心
    public static final int REDIRECT_TO_TRADECENTER_CODE = 401000;
    public static final String REDIRECT_TO_TRADECENTER_MESSAGE = "/web/tradeCenter/show";


    /**
     * 交易中心和客服页面消息开始
     */
    //返回成功
    public static final int SUCCESS_OPT_CODE = 401001;
    public static final String SUCCESS_OPT_MESSAGE = "查询成功";

    //操作频繁
    public static final int OPERATING_FREQUENCY_CODE = 401002;
    public static final String OPERATING_FREQUENCY_MESSAGE = "用户操作频繁";

    //用户不存在
    public static final int USER_NOT_EXIST_CODE = 401003;
    public static final String USER_NOT_EXIST_MESSAGE = "该用户不存在";

    //用户未登录
    public static final int NOT_LOGININ_CODE = 401004;
    public static final String NOT_LOGININ_MESSAGE = "未登录";


    //全部币种没有上线
    public static final int CURRENCY_NOTONLINE_CODE = 401005;
    public static final String CURRENCYNOTONLINE_MESSAGE = "没有上线币种";

    //用户账号被禁用
    public static final int ACCOUNT_DISABLED_CODE = 401006;
    public static final String ACCOUNT_DISABLED_MESSAGE = "该账号已被禁用";

    //币种已下线
    public static final int CURRENCY_OFFLINE_CODE = 401007;
    public static final String CURRENCY_OFFLINE_MESSAGE = "该币种已下线";

    //币种不在交易状态
    public static final int CURRENCY_NOT_TRADED_CODE = 401008;
    public static final String CURRENCY_NOT_TRADED_MESSAGE = "该币种不在交易状态";

    //不在交易时间
    public static final int NOT_IN_TRADED_TIME_CODE = 401009;
    public static final String NOT_IN_TRADED_TIME_MESSAGE = "不在交易时间段内";
    //交易数量
    public static final int TRADE_NUM_WRONG_CODE = 401010;
    public static final String TRADE_NUM_WRONG_MESSAGE = "交易数量不能小于等于0";
    //交易价格
    public static final int TRADE_PRICE_WRONG_CODE = 401011;
    public static final String TRADE_PRICE_WRONG_MESSAGE = "交易单价不能小于等于0";
    //支付密码错误
    public static final int TRADE_PASS_WRONG_CODE = 401012;
    public static final String TRADE_PASS_WRONG_MESSAGE = "支付密码错误";
    //余额不足
    public static final int MONEY_NOT_ENOUGH_CODE = 401013;
    public static final String MONEY_NOT_ENOUGH_MESSAGE = "用户余额不足";
    //挂单失败
    public static final int PEND_FAILURE_CODE = 401014;
    public static final String PEND_FAILURE_MESSAGE = "挂单失败";
    //挂单成功
    public static final int PEND_SUCCESS_CODE = 401015;
    public static final String PEND_SUCCESS_MESSAGE = "挂单成功";
    //币种信息不存在
    public static final int NOT_HAVE_CURRENCY_INFO_CODE = 401016;
    public static final String NOT_HAVE_CURRENCY_INFO_MESSAGE = "币种信息获取失败,请稍候再试";
    //币不足
    public static final int COIN_NOT_ENOUGH_CODE = 401017;
    public static final String COIN_NOT_ENOUGH_MESSAGE = "用户币不足";
    //修改成功
    public static final int MODIFY_SUCCESS_CODE = 401018;
    public static final String MODIFY_SUCCESS_MESSAGE = "修改成功";
    //修改失败
    public static final int MODIFY_FAILD_CODE = 401019;
    public static final String MODIFY_FAILD_MESSAGE = "修改失败";
    //操作失败
    public static final int OPERATION_ERROR_CODE = 401020;
    public static final String OPERATION_ERROR_MESSATE = "操作失败";
    //查询无结果
    public static final int NO_RESULT_CODE = 401020;
    public static final String NO_RESULT_MESSAGE = "查询无结果";
    //修改成功
    public static final int COMMIT_SUCCESS_CODE = 100401;
    public static final String COMMIT_SUCCESS_MESSAGE = "提交成功";
    //修改失败
    public static final int COMMIT_FAILD_CODE = 100402;
    public static final String COMMIT_FAILD_MESSAGE = "提交失败";
    //参数不能为空
    public static final int PARAMETER_NOT_BE_NULL_CODE = 100403;
    public static final String PARAMETER_NOT_BE_NULL_MESSAGE = "参数不能为空";
    /**
     * 交易中心和客服结束
     */

}
