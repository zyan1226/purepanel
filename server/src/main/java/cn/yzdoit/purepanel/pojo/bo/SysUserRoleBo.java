package cn.yzdoit.purepanel.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 闫政
 * @since 2025/7/8 05:41 星期二
 */
@Data
public class SysUserRoleBo {

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private String roleId;
}
