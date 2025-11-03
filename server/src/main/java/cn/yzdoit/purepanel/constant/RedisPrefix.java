package cn.yzdoit.purepanel.constant;


/**
 * Redis 键前缀
 *
 * @author 闫政
 * @since 2025/6/23 19:30 周一
 */
public class RedisPrefix {

    /**
     * 登录验证码
     */
    public static final String SYS_CAPTCHA = "SYS:CAPTCHA:";

    /**
     * 登录状态存储
     */
    public static final String SYS_LOGIN_STATE = "SYS:LOGIN_STATE:";

    /**
     * 用户最新LoginCode
     */
    public static final String SYS_LATEST_LOGIN_CODE = "SYS:LATEST_LOGIN_CODE:";
}
