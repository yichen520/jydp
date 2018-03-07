package config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: 手机号-国际区号配置
 * Author: hht
 * Date: 2018-02-28 13:47
 */
public class PhoneAreaConfig {

    /** 手机号-国际区号-初始化值 */
    public static final Map<String, String> phoneAreaMap = new LinkedHashMap<String, String>(){
        private static final long serialVersionUID = 1L;{
            put("+86", "中国");
            put("+1", "美国");
            put("+61", "澳大利亚");
            put("+994", "阿塞拜疆");
            put("+998", "乌兹别克斯坦");
        }
    };

    /** 国际区号-中国 */
    public static final String PHONE_AREA_CHINA = "+86";

    /** 国际区号-美国 */
    public static final String PHONE_AREA_USA = "+1";

    /** 国际区号-澳大利亚 */
    public static final String PHONE_AREA_AUSTRALIA = "+61";

    /** 国际区号-阿塞拜疆 */
    public static final String PHONE_AREA_AZERBAIJAN = "+994";

    /** 国际区号-乌兹别克斯坦 */
    public static final String PHONE_AREA_UZBEKISTAN = "+998";
}
