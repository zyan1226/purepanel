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
import java.io.Serializable;

/**
 * 群组信息表
 *
 * @author 闫政
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "sys_group")
@Schema(description = "群组信息表")
@EqualsAndHashCode(callSuper = true)
public class SysGroup extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 4138591747213204128L;

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @Schema(description = "父级id")
    private String pid;

    /**
     * 名称
     */
    @TableField(value = "name")
    @Schema(description = "名称")
    private String name;

    /**
     * 排序号
     */
    @TableField(value = "order_no")
    @Schema(description = "排序号")
    private Integer orderNo;
}
