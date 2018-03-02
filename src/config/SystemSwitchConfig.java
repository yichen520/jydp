package config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统开关编码表
 * @author whx
 */
public class SystemSwitchConfig {

    /** 系统开关编码表 */
    public static final Map<Integer, String> switchCodeMap = new LinkedHashMap<Integer, String>(){
        private static final long serialVersionUID = 1L;{
            put(100000, "核对用户账户");
        }
    };

    /** 核对用户美金账户，数字货币账户开关编码 */
    public static final int CHECK_AMOUNT_CODE = 100000;

}
