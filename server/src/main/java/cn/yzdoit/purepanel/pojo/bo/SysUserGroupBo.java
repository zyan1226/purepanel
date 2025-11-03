package cn.yzdoit.purepanel.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 闫政
 * @since 2025/7/8 05:41 星期二
 */
@Data
public class SysUserGroupBo {

    /**
     * 群组名称
     */
    @Schema(description = "群组名称")
    private String name;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 群组id
     */
    @Schema(description = "群组id")
    private String groupId;
}
