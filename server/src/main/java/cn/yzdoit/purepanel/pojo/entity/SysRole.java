package cn.yzdoit.purepanel.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 角色定义表
 *
 * @author 闫政
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "sys_role")
@Schema(description = "角色定义表")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2463404245381848563L;

    /**
     * 名称
     */
    @TableField(value = "name")
    @Schema(description = "名称")
    private String name;
}
