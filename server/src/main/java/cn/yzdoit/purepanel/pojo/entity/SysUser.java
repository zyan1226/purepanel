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
 * 用户信息表
 *
 * @author 闫政
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "sys_user")
@Schema(description = "用户信息表")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2463404245381848563L;

    /**
     * 名称
     */
    @TableField(value = "name")
    @Schema(description = "名称")
    private String name;

    /**
     * 账号 全局唯一
     */
    @TableField(value = "account")
    @Schema(description = "账号 全局唯一")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "pwd")
    @Schema(description = "密码")
    private String pwd;

    /**
     * 密码盐
     */
    @TableField(value = "salt")
    @Schema(description = "密码盐")
    private String salt;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @Schema(description = "头像")
    private String avatar;

    /**
     * 状态 0禁用 1启用
     */
    @TableField(value = "status")
    @Schema(description = "状态 0禁用 1启用")
    private Boolean status;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "phone_number")
    @Schema(description = "手机号")
    private String phoneNumber;
}
