package cn.yzdoit.purepanel.pojo.req;

import cn.yzdoit.purepanel.pojo.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * @author 闫政
 * @since 2025/7/17 14:40 星期四
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSaveReq extends SysUser {
    @Serial
    private static final long serialVersionUID = 8846499516927140240L;

    /**
     * 所属群组列表
     */
    @Schema(description = "所属群组列表")
    private List<String> groupIdList;

    /**
     * 所属角色列表
     */
    @Schema(description = "所属角色列表")
    private List<String> roleIdList;
}
