package cn.yzdoit.purepanel.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @author 闫政
 * @since 2025/6/17 14:41 星期二
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3323868555609407391L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID, value = "id")
    @Schema(description = "主键")
    private String id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by_user_id")
    @Schema(description = "创建人")
    private String createByUserId;

    /**
     * 更新人
     */
    @TableField(value = "update_by_user_id")
    @Schema(description = "更新人")
    private String updateByUserId;

    /**
     * 数据版本
     */
    @Version
    @TableField(value = "version")
    @Schema(description = "数据版本")
    private Integer version;
}
