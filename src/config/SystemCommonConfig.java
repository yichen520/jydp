package config;

/**
 * 系统常规配置
 * 主业务（35位【1~9 A~Z】）+子业务（36位【0~9 A~Z】）
 * @author whx
 *
 */
public class SystemCommonConfig {

	/** 用户登录记录 -业务类型（2）+日期（6）+随机位（12） */
	public static final String LOGIN_USER = "10";
	/** 后台管理员登录记录 -业务类型（2）+日期（6）+随机位（12） */
	public static final String LOGIN_BACKER = "11";

	/** 用户账户记录 -业务类型（2）+日期（6）+随机位（10） */
	public static final String USER_BALANCE = "20";
	/** 用户货币记录 -业务类型（2）+日期（6）+随机位（10） */
	public static final String USER_CURRENCY = "21";

	/** 挂单记录 -业务类型（2）+日期（6）+随机位（10） */
	public static final String TRANSACTION_PEND_ORDER = "30";
	/** 成交记录 -业务类型（2）+日期（6）+随机位（10） */
	public static final String TRANSACTION_USER_DEAL = "31";
	/** 做单记录 -业务类型（2）+日期（6）+随机位（10） */
	public static final String TRANSACTION_MAKE_ORDER = "32";

	/** 用户账户可用资产记录 -业务类型（2）+日期（6）+随机位（10） */
	public static final String AMOUNT_BALANCE_USER = "41";
}
