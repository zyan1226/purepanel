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
 * 用户角色关联表
 *
 * @author 闫政
 * @since 2025/6/23 22:54 周一
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "sys_user_role")
@Schema(description = "用户角色关联表")
public class SysUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = -278497507995143297L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id")
    @Schema(description = "主键")
    private String id;

    /**
     * 用户 ID
     */
    @TableField(value = "user_id")
    @Schema(description = "用户 ID")
    private String userId;

    /**
     * 角色 ID
     */
    @TableField(value = "role_id")
    @Schema(description = "角色 ID")
    private String roleId;
}
