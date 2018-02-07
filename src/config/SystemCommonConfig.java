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

}
