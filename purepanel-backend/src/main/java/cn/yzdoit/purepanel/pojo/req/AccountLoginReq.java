package cn.yzdoit.purepanel.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 账号登录接口请求参数
 *
 * @author 闫政
 * @since 2025/6/23 08:58 星期一
 */
@Data
public class AccountLoginReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 3344680460224958809L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String pwd;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    /**
     * 验证码key
     */
    @Schema(description = "验证码key")
    @NotBlank(message = "验证码key不能为空")
    private String captchaKey;
}
