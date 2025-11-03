package cn.yzdoit.purepanel.pojo.res;


import cn.yzdoit.purepanel.pojo.entity.SysPermission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 获取用户权限接口响应参数
 *
 * @author 闫政
 * @since 2025/6/23 23:16 周一
 */
@Data
@SuperBuilder
@Schema(description = "获取用户权限接口响应参数")
public class GetUserPermissionRes implements Serializable {

    @Serial
    private static final long serialVersionUID = -4094662321270393724L;

    /**
     * 所有权限树(菜单+按钮)
     */
    @Schema(description = "所有权限树(菜单+按钮)")
    private List<SysPermissionTreeListRes> permissionTree;

    /**
     * 菜单树
     */
    @Schema(description = "菜单树")
    private List<SysPermissionTreeListRes> menuTree;

    /**
     * 按钮列表
     */
    @Schema(description = "按钮列表")
    private List<SysPermission> pageElementList;

    public static GetUserPermissionRes empty() {
        return GetUserPermissionRes.builder().build();
    }
}
