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
 * AI 聊天会话
 *
 * @author 闫政
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "ai_chatbot_conversation")
@Schema(description = "AI 聊天会话")
public class AiChatbotConversation implements Serializable {

    @Serial
    private static final long serialVersionUID = 4375779826943970828L;

    /**
     * 唯一主键
     */
    @TableId(type = IdType.INPUT, value = "id")
    @Schema(description = "唯一主键")
    private String id;

    /**
     * 会话标题
     */
    @TableField(value = "title")
    @Schema(description = "会话标题")
    private String title;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @Schema(description = "用户ID")
    private String userId;

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
