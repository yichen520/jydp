package config;

/**
 * session配置
 * @author whx
 */
public class SessionConfig {

    /** 用户id前缀 */
    public static String SESSION_USER_ID = "userId_";
    /** 后台管理员id前缀 */
    public static String SESSION_BACKER_ID = "backerId_";
    /** sessionId前缀 */
    public static String SESSION_ID_PREFIX = "spring:session:";
    /** sessions: */
    public static String SESSION_SESSIONS = SESSION_ID_PREFIX + "sessions:";
    /** sessions:expires: */
    public static String SESSION_EXPIRES = SESSION_ID_PREFIX + "sessions:expires:";
    /** expirations: */
    public static String SESSION_EXPIRATIONS = SESSION_ID_PREFIX + "expirations:";
    /** 用户sessionAttr:userSession */
    public static String SESSION_USER_ATTR = "sessionAttr:userSession";
    /** 后台管理员sessionAttr:backerSession */
    public static String SESSION_BACKER_ATTR = "sessionAttr:backerSession";
    /** 后台管理员权限sessionAttr:backer_rolePower */
    public static String SESSION_BACKER_POWER= "sessionAttr:backer_rolePower";
    /** session销毁时间(秒) */
    public static int SESSION_TIME_OUT = 3600;

}
