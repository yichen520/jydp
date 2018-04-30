package config;

import org.springframework.amqp.rabbit.support.PublisherCallbackChannelImpl;

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
    public static final int SYSTEM_CODE_SUCCESS = 1;
    public static final String SYSTEM_MESSAGE_SUCCESS = "操作成功";
    //参数错误
    public static final int SYSTEM_CODE_PARAM_ERROR = 2;
    public static final String SYSTEM_MESSAGE_PARAM_ERROR = "参数错误";
    //JSON格式错误
    public static final int SYSTEM_CODE_JSON_ERROR = 3;
    public static final String SYSTEM_MESSAGE_JSON_ERROR = "JSON格式错误";
    //身份过期
    public static final int SYSTEM_CODE_LOGIN_EXPIRED = 4;
    public static final String SYSTEM_MESSAGE_LOGIN_EXPIRED = "身份过期";
    //服务器异常
    public static final int SYSTEM_CODE_SERVER_EXCEPTION = 5;
    public static final String SYSTEM_MESSAGE_SERVER_EXCEPTION = "服务器异常";

    public static final int SYSTEM_CODE_NO_RESULT = 6;
    public static final String SYSTEM_MESSAGE_NO_RESULT = "查询无结果";

    /**
     * 个人中心用户信息模块
     */
    //用户不存在
    public static final int CODE_USER_NOT_EXIST = 103001;
    public static final String MESSAGE_USER_NOT_EXIST = "用户不存在";
    //查询无结果
    public static final int CODE_NO_RESULT = 103002;
    public static final String MESSAGE_NO_RESULT = "查询无结果";
    //两次输入密码不一致
    public static final int CODE_PASSWORD_NOT_IDENTICAL = 103003;
    public static final String MESSAGE_PASSWORD_NOT_IDENTICAL = "两次输入密码不一致";
    //新密码与原密码相同
    public static final int CODE_PASSWORD_OLD_NEW_COMMON = 103004;
    public static final String MESSAGE_PASSWORD_OLD_NEW_COMMON = "新密码不可与原密码相同";
    //用户信息查询失败
    public static final int CODE_USER_INFO_NULL = 103005;
    public static final String MESSAGE_USER_INFO_NULL = "用户信息查询失败";
    //原密码错误
    public static final int CODE_PASSWORD_OLD_ERROR = 103006;
    public static final String MESSAGE_PASSWORD_OLD_ERROR = "原密码错误";
    //与支付密码相同
    public static final int CODE_PASSWORD_WITH_PAYPASSWORD_COMMON = 103007;
    public static final String MESSAGE_PASSWORD_WITH_PAYPASSWORD_COMMON = "与支付密码相同";
    //操作失败
    public static final int CODE_OPERATE_ERROR = 103008;
    public static final String MESSAGE_OPERATE_ERROR = "操作失败";
    //与登录密码相同
    public static final int CODE_PAYPASSWORD_WITH_PASSWORD_COMMON = 103009;
    public static final String MESSAGE_PAYPASSWORD_WITH_PASSWORD_COMMON = "与登录密码相同";
    //新手机号与原手机号相同
    public static final int CODE_PHONE_OLD_NEW_COMMON = 103010;
    public static final String MESSAGE_PHONE_OLD_NEW_COMMON = "新手机号与原手机号相同";
    //登录密码错误
    public static final int CODE_PASSWORD_ERROR = 103011;
    public static final String MESSAGE_PASSWORD_ERROR = "登录密码错误";
    //手机号已被绑定
    public static final int CODE_PHONE_BIND = 103012;
    public static final String MESSAGE_PHONE_BIND = "手机号已被绑定";
    //查询成功
    public static final String SYSTEM_MESSAGE__GET_SUCCESS = "查询成功";


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

    //系统公告
    public static final int REDIRECT_TO_SYSNOTICE_CODE = 601001;
    public static final String REDIRECT_TO_SYSNOTICE_MESSAGE = "/web/webSystemNotice/show";

    //系统公告详情
    public static final int REDIRECT_TO_SYSNOTICEDETAIL_CODE = 601002;
    public static final String REDIRECT_TO_SYSNOTICEDETAIL_MESSAGE = "/web/webSystemNotice/show";


    //热门话题
    public static final int REDIRECT_TO_SYSHOT_CODE = 602001;
    public static final String REDIRECT_TO_SYSHOT_MESSAGE = "/web/webSystemHot/show";

    //热门话题详情
    public static final int REDIRECT_TO_SYSHOTDETAIL_CODE = 602002;
    public static final String REDIRECT_TO_SYSHOTDETAIL_MESSAGE = "page/web/systemHotDetail";


    /**
     * 交易中心和客服页面消息开始
     */
    //返回成功
    public static final int SUCCESS_OPT_CODE = 1;
    public static final String SUCCESS_OPT_MESSAGE = "查询成功";

    //操作频繁
    public static final int OPERATING_FREQUENCY_CODE = 401002;
    public static final String OPERATING_FREQUENCY_MESSAGE = "用户操作频繁";

    //用户不存在
    public static final int USER_NOT_EXIST_CODE = 401003;
    public static final String USER_NOT_EXIST_MESSAGE = "该用户不存在";

    //用户未登录
    public static final int NOT_LOGININ_CODE = 401004;
    public static final String NOT_LOGININ_MESSAGE = "用户未登录";


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
    public static final int COMMIT_SUCCESS_CODE = 1;
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

    /**
     * 登录接口状态码和信息开始
     */

    //登录成功
    public static final int LOGIN_SUCCESS_CODE = 201001;
    public static final String LOGIN_SUCCESS_MESSAGE = "登录成功";
    //账号或密码为空
    public static final int USER_ACCOUNT_OR_PASSWORD_ISNULL_CODE = 201002;
    public static final String USER_ACCOUNT_OR_PASSWORD_ISNULL_MESSAGE = "账号或密码不能为空";
    //账号或密码错误
    public static final int USER_ACCOUNT_OR_PASSWORD_ERROR_CODE = 201003;
    public static final String USER_ACCOUNT_OR_PASSWORD_ERROR_MESSAGE = "账号或密码错误";
    //登出成功
    public static final int LOGOUT_SUCCESS_CODE = 201004;
    public static final String LOGOUT_SUCCESS_MESSAGE = "登出成功";

    /** 登录接口状态码和信息结束 */

    /**
     * 找回密码接口状态码和信息开始
     */

    //参数为空
    public static final int PARAMETER_ISNULL_CODE = 204002;
    public static final String PARAMETER_ISNULL_MESSAGE = "参数不能为空";
    //用户不存在
    public static final int USER_ISEXIST_CODE = 204003;
    public static final String USER_ISEXIST_MESSAGE = "用户不存在";
    //用户被禁用
    public static final int USER_ISDISABLED_CODE = 204004;
    public static final String USER_ISDISABLED_MESSAGE = "用户被禁用";
    //手机号与用户所绑手机号不匹配
    public static final int PHONENUMBER_AND_USERACCOUNT_NOTMATCHING_CODE = 204005;
    public static final String PHONENUMBER_AND_USERACCOUNT_NOTMATCHING_MESSAGE = "手机号与用户所绑手机号不匹配";
    //不可与支付密码相同
    public static final int PASSWORD_IDENTICAL_CODE = 204006;
    public static final String PASSWORD_IDENTICAL_MESSAGE = "不可与支付密码相同";
    //不可与支付密码相同
    public static final int BACK_SUCCESS_CODE = 204001;
    public static final String BACK_SUCCESS_MESSAGE = "找回密码成功";
    //找回密码失败
    public static final int BACK_FAIL_CODE = 204007;
    public static final String BACK_FAIL_MESSAGE = "找回密码失败";
    //验证码错误
    public static final int VALIDATECODE_ISERROR_CODE = 204008;
    public static final String VALIDATECODE_ISERROR_MESSAGE = "验证码错误";

    /** 找回密码接口状态码和信息结束*/


    /**
     * 用户注册状态码和信息
     */
    //用户名重复
    public static final int ACCOUNT_REPEAT_CODE = 2020001;
    public static final String ACCOUNT_REPEAT_MESSAGE = "用户名重复";
    //该手机号已被注册
    public static final int PHONE_REGISTERED_CODE = 2020002;
    public static final String PHONE_REGISTERED_MESSAGE = "该手机号已被注册";
    //注册成功
    public static final int REGISTER_SUCCESS_CODE = 2020003;
    public static final String REGISTER_SUCCESS_MESSAGE = "注册成功";
    //注册失败
    public static final int REGISTER_FAIL_CODE = 2020004;
    public static final String REGISTER_FAIL_MESSAGE = "注册失败";
    //用户名可用
    public static final int ACCOUNT_AVAILABLE_CODE = 2020005;
    public static final String ACCOUNT_AVAILABLE_MESSAGE = "用户名可用";

    /**
     * 个人中心我的记录
     */

    //撤销失败
    public static final int UNDO_FAILED_CODE = 1050001;
    public static final String UNDO_FAILED_MESSAGE = "撤销失败";

    //此操作非该挂单本人
    public static final int OPERATION_NOT_PENDING_ORDER_CODE = 1050002;
    public static final String OPERATION_NOT_PENDING_ORDER_MESSAGE = "此操作非该挂单本人";


    /**
     * 用户认证接口状态码和信息开始
     */
    // 未进行认证
    public static final int NOIDENTIFICATION_CODE = 203002;
    public static final String NOIDENTIFICATION_MESSAGE = "未进行认证";
    // 未通过认证
    public static final int NOADOPT_CODE = 203003;
    public static final String NOADOPT_MESSAGE = "未通过认证";
    // 审核拒绝
    public static final int REFUE_CODE = 203004;
    public static final String REFUE_MESSAGE = "审核拒绝";
    // 认证失败
    public static final int IFICATION_FAIL_CODE = 203005;
    public static final String IFICATION_FAIL_MESSAGE = "认证失败";
    // 操作频繁
    public static final int FREQUENT_OPERATION_CODE = 203006;
    public static final String FREQUENT_OPERATION_MESSAGE = "操作频繁";
    // 已有认证信息通过
    public static final int IFICATION_ISEXIST_CODE = 203007;
    public static final String IFICATION_ISEXIST_MESSAGE = "已有认证信息通过";
    // 已有认证信息在审核中
    public static final int IFICATION_CONDUCT_CODE = 203008;
    public static final String IFICATION_CONDUCT_MESSAGE = "已有认证信息在审核中";

    /** 用户认证接口状态码和信息结束*/


    /**
     * 场外交易
     */
    //操作频繁
    public static final int CODE_OPERATE_FREQUENT = 700001;
    public static final String MESSAGE_OPERATE_FREQUENT = "用户操作频繁";
    //用户不是经销商
    public static final int CODE_USER_NOT_DEALER = 700002;
    public static final String MESSAGE_USER_NOT_DEALER = "用户不是经销商";
    //订单不存在
    public static final int CODE_ORDER_NOT_EXIST = 700003;
    public static final String MESSAGE_ORDER_NOT_EXIST = "订单不存在";
    //非法访问
    public static final int CODE_VISIT_ILLEGAL = 700004;
    public static final String MESSAGE_VISIT_ILLEGAL = "非法访问";
    //订单已完成
    public static final int CODE_ORDER_FINISHED = 700005;
    public static final String MESSAGE_ORDER_FINISHED = "订单已完成";
    //非法类型
    public static final int CODE_TYPE_ILLEGAL = 700006;
    public static final String MESSAGE_TYPE_ILLEGAL = "非法类型";
}
