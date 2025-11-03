package cn.yzdoit.purepanel.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author 闫政
 * @since 2025/7/17 11:21 星期四
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageListReq extends PageReq {
    @Serial
    private static final long serialVersionUID = -1651040833894940766L;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
}
