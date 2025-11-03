package cn.yzdoit.purepanel.pojo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Purepanel 配置
 *
 * @author 闫政
 * @since 2025/6/17 16:50 星期二
 */
@Data
@ConfigurationProperties(prefix = "purepanel")
public class PurepanelProperties {

    /**
     * 是否开启接口异常信息返回
     */
    private Boolean resExceptionEnabled = Boolean.TRUE;


    /**
     * 本地（磁盘）对象存储的基础路径
     */
    private String localObjectStoreBasePath;

    /**
     * 登录配置
     */
    private LoginConfig loginConfig;

    /**
     * 根账号ID
     * <p>
     * 该账号会被保护，且拥有系统最高权限
     */
    private String rootUserId;

    /**
     * 登录相关配置
     */
    @Data
    public static class LoginConfig {

        /**
         * 单会话登录 一个账号多次登录会互相挤下线
         */
        private Boolean singleSessionLoginEnabled = Boolean.TRUE;

        /**
         * 登录过期时间 单位小时
         */
        private Integer loginStatusExpireTime = 24 * 5;
    }
}
