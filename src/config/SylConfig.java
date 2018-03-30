package config;

/**
 * Description:盛源链接口配置
 * Author: sy
 * Date: 2018-03-22
 */
public class SylConfig {
    /**
     * 签名秘钥
     */
    public static final String SIGN_SECRET_KEY = "SLJZ@JYDP";

    /**
     * 盛源链钱包平台服务器根路径
     **/
    public static final String ROOT_URL = "http://192.168.12.123:8080/";

    /**
     * 交易大盘向盛源链钱包转币申请接口
     **/
    public static final String WITHDRAWALS_COIN_APPLY_URL = ROOT_URL + "withdrawalsCoinApply";

    /**
     * 盛源链简称
     */
    public static final String SHENYUAN_COIN = "MUC";
}

