package cn.yzdoit.purepanel.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色权限关联表
 *
 * @author 闫政
 * @since 2025/6/23 23:06 周一
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "sys_role_permission")
@Schema(description = "角色权限关联表")
public class SysRolePermission implements Serializable {

    @Serial
    private static final long serialVersionUID = -6576464630244575949L;


    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id")
    @Schema(description = "主键")
    private String id;

    /**
     * 角色 ID
     */
    @TableField(value = "role_id")
    @Schema(description = "角色 ID")
    private String roleId;

    /**
     * 权限 ID
     */
    @TableField(value = "permission_id")
    @Schema(description = "权限 ID")
    private String permissionId;
}
