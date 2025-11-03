package cn.yzdoit.purepanel.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户分页列表请求参数
 *
 * @author 闫政
 * @since 2025/7/4 05:37 星期五
 */
@Data
@Schema(description = "用户分页列表请求参数")
@EqualsAndHashCode(callSuper = true)
public class UserPageListReq extends PageReq {
    @Serial
    private static final long serialVersionUID = 2392566834790654246L;


    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;
}
