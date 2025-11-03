package cn.yzdoit.purepanel.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态枚举
 * @author 闫政
 * @since 2025/6/23 14:36 星期一
 */
@Getter
@AllArgsConstructor
public enum ApiStatusEnum {

    /**
     * 成功
     */
    SUCCESS(200, "处理成功"),

    /**
     * 登录超时，需要重新登录
     */
    UNAUTHORIZED(401, "登录超时，需要重新登录"),

    /**
     * 无操作权限
     */
    NO_PERMISSIONS(403, "无操作权限"),

    /**
     * 资源未找到
     */
    NOT_FOUND(404, "资源未找到"),

    /**
     * 失败
     */
    FAIL(500, "服务繁忙，请稍后再试");

    /**
     * 状态码
     */
    private final Integer status;

    /**
     * 提示消息
     */
    private final String msg;
}