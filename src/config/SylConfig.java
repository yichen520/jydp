package config;

/**
 * Description:盛源链接口配置
 * Author: sy
 * Date: 2018-03-22
 */
public class SylConfig {
    /** 签名秘钥 */
    public static final String SIGN_SECRET_KEY = "SLJZ@JYDP";

    /** 盛源链钱包平台服务器根路径 **/
    public static final String ROOT_URL = "http://test.oksheng.com.cn/syl/";

    /** 绑定用户接口 **/
    public static final String WITHDRAWALS_COIN_APPLY_URL = ROOT_URL + "withdrawalsCoinApply";
}
