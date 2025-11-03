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
 * 权限定义表
 *
 * @author 闫政
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "权限定义表")
@TableName(value = "sys_permission")
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -5624938720200922829L;

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @Schema(description = "父级id")
    private String pid;

    /**
     * 菜单类型 0菜单 1页面元素 2菜单组
     */
    @TableField(value = "type")
    @Schema(description = "菜单类型 0菜单 1页面元素 2菜单组")
    private Integer type;

    /**
     * 访问路径
     */
    @TableField(value = "path")
    @Schema(description = "访问路径")
    private String path;

    /**
     * 标题
     */
    @TableField(value = "title")
    @Schema(description = "标题")
    private String title;

    /**
     * 图标
     */
    @TableField(value = "icon")
    @Schema(description = "图标")
    private String icon;

    /**
     * 菜单展示顺序 数字越小越靠前
     */
    @TableField(value = "order_no")
    @Schema(description = "菜单展示顺序 数字越小越靠前")
    private Integer orderNo;

    /**
     * 是否隐藏 0否1是
     */
    @TableField(value = "is_hidden")
    @Schema(description = "是否隐藏 0否1是")
    private Boolean isHidden;

    /**
     * 菜单层级
     */
    @TableField(value = "menu_level")
    @Schema(description = "菜单层级")
    private Integer menuLevel;

    /**
     * 授权编码
     */
    @TableField(value = "auth_code")
    @Schema(description = "授权编码")
    private String authCode;
}
