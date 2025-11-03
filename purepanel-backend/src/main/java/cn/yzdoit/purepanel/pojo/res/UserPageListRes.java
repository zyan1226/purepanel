package cn.yzdoit.purepanel.pojo.res;

import cn.yzdoit.purepanel.pojo.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 用户分页列表返回参数
 *
 * @author 闫政
 * @since 2025/7/4 05:42 星期五
 */
@Data
@Schema(description = "用户分页列表返回参数")
@EqualsAndHashCode(callSuper = true)
public class UserPageListRes extends SysUser {
    @Serial
    private static final long serialVersionUID = -7618480080516525829L;

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

    /**
     * 所属群组名称
     */
    @Schema(description = "所属群组名称")
    private String groupNames;

    /**
     * 所属角色名称
     */
    @Schema(description = "所属角色名称")
    private String roleNames;
}
