package cn.yzdoit.purepanel.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * AI 聊天请求参数
 *
 * @author 闫政
 * @since 2025/8/22 10:31 星期五
 */
@Data
@Schema(description = "AI 聊天请求参数")
public class AIChatReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 2364400162814980191L;

    /**
     * 问题
     */
    @Schema(description = "问题")
    private String question;

    /**
     * 会话id
     */
    @Schema(description = "会话id")
    private String conversationId;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String modelName;
}
