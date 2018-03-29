package config;

/**
 * k线图统计数据配置
 * @author whx
 */
public class KGraphConfig {

    public static final long one = 60 * 1000L;//1m
    /** 五分钟节点 */
    public static final long FIVE = 5 * 60 * 1000L;//5m
    /** 十五分钟节点 */
    public static final long FIFTEEN = 15 * 60 * 1000L;//15m
    /** 三十分钟节点 */
    public static final long HALFHOUR = 30 * 60 * 1000L;//30m
    /** 一小时节点 */
    public static final long ONEHOURS = 60 * 60 * 1000L;//1h
    /** 四小时节点 */
    public static final long FOREHOURS = 4 * 60 * 60 * 1000L;//4h
    /** 一天节点 */
    public static final long ONEDAY = 24 * 60 * 60 * 1000L;//1d
    /** 一周节点 */
    public static final long ONEWEEK = 7 * 24 * 60 * 60 * 1000L;//1w

}
