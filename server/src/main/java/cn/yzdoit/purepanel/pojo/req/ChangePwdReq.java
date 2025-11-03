package cn.yzdoit.purepanel.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改密码请求参数
 *
 * @author 闫政
 * @since 2025/7/2 05:28 星期三
 */
@Data
@Schema(description = "修改密码请求参数")
public class ChangePwdReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 5153910505158784893L;

    /**
     * 旧密码
     */
    @Schema(description = "旧密码")
    private String oldPwd;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    private String newPwd;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String userId;

    /**
     * 是否自行修改密码
     */
    private Boolean isSelfChange;
}
