package cn.yzdoit.purepanel.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI 聊天记录
 *
 * @author 闫政
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "ai_chatbot_chat_record")
@Schema(description = "AI 聊天记录")
public class AiChatbotChatRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 3607606616234531011L;

    /**
     * 唯一主键
     */
    @TableId(type = IdType.ASSIGN_UUID, value = "id")
    @Schema(description = "唯一主键")
    private String id;

    /**
     * 会话ID
     */
    @TableField(value = "conversation_id")
    @Schema(description = "会话ID")
    private String conversationId;

    /**
     * 类型 USER/ASSISTANT
     */
    @TableField(value = "type")
    @Schema(description = "类型 USER/ASSISTANT")
    private String type;

    /**
     * 内容
     */
    @TableField(value = "content")
    @Schema(description = "内容")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;


}
