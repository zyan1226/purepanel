package cn.yzdoit.purepanel.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 获取验证码接口响应参数
 *
 * @author 闫政
 * @since 2025/6/23 07:04 星期一
 */
@Data
@SuperBuilder
@Schema(description = "获取验证码接口响应参数")
public class GetCaptchaRes implements Serializable {
    @Serial
    private static final long serialVersionUID = -2176690216663895662L;

    /**
     * 验证码key
     */
    @Schema(description = "验证码key")
    private String captchaKey;

    /**
     * 验证码图片
     */
    @Schema(description = "验证码图片")
    private String captchaBase64;
}
