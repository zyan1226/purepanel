package cn.yzdoit.purepanel.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页接口公共请求参数
 *
 * @author 闫政
 * @since 2025/7/4 05:35 星期五
 */
@Data
@Schema(description = "分页接口公共请求参数")
public class PageReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 1770229421208050128L;

    /**
     * 当前页码
     */
    @Min(value = 1, message = "当前页码不能小于1")
    @NotNull(message = "当前页码不能为空")
    @Schema(description = "当前页码")
    private Long current;

    /**
     * 每页显示条数
     */
    @Min(value = 1, message = "每页显示条数不能小于1")
    @NotNull(message = "每页显示条数不能为空")
    @Schema(description = "每页显示条数")
    private Long size;
}
