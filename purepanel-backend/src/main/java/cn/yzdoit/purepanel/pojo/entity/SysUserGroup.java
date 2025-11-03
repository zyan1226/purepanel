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
 * 用户群组关联表
 *
 * @author 闫政
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "sys_user_group")
@Schema(description = "用户群组关联表")
public class SysUserGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = 1839291917357733486L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id")
    @Schema(description = "主键")
    private String id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 群组ID
     */
    @TableField(value = "group_id")
    @Schema(description = "群组ID")
    private String groupId;

}

