package cn.yzdoit.purepanel.pojo.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 为角色授权接口请求参数
 *
 * @author 闫政
 * @since 2025/7/24 23:25 周四
 */
@Data
@Schema(description = "角色ID")
public class RoleBindPermissionReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 8771465999285693608L;


    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private String roleId;

    /**
     * 权限ID集合
     */
    @Schema(description = "权限ID集合")
    private Set<String> permissionIdSet;
}
