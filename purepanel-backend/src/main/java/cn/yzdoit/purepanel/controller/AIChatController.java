package cn.yzdoit.purepanel.controller;

import cn.yzdoit.purepanel.pojo.entity.AiChatbotChatRecord;
import cn.yzdoit.purepanel.pojo.entity.AiChatbotConversation;
import cn.yzdoit.purepanel.pojo.req.AIChatReq;
import cn.yzdoit.purepanel.pojo.res.Res;
import cn.yzdoit.purepanel.service.AIChatService;
import cn.yzdoit.purepanel.service.AiChatbotChatRecordService;
import cn.yzdoit.purepanel.service.AiChatbotConversationService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 聊天机器人相关接口
 *
 * @author 闫政
 * @since 2025/8/18 17:20 星期一
 */
@RestController
@RequestMapping("/chatbot")
@RequiredArgsConstructor
@Tag(name = "聊天机器人相关接口")
public class AIChatController {

    private final AIChatService aiChatService;
    private final AiChatbotConversationService aiChatbotConversationService;
    private final AiChatbotChatRecordService aiChatbotChatRecordService;

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "AI 聊天")
    public Flux<String> chat(@RequestBody AIChatReq req, @RequestAttribute String loginUserId) {
        return aiChatService.chat(req, loginUserId);
    }

    @GetMapping("listConversation")
    @Operation(summary = "查询会话列表")
    public Res<List<AiChatbotConversation>> listConversation(@RequestAttribute String loginUserId) {
        return Res.success(aiChatbotConversationService.list(Wrappers.<AiChatbotConversation>lambdaQuery()
                .eq(AiChatbotConversation::getUserId, loginUserId)
                .orderByDesc(AiChatbotConversation::getCreateTime)));
    }

    @GetMapping("/listChatRecord")
    @Operation(summary = "查询聊天记录")
    public Res<List<AiChatbotChatRecord>> listChatRecord(@RequestParam String conversationId) {
        return Res.success(aiChatbotChatRecordService.list(Wrappers.<AiChatbotChatRecord>lambdaQuery()
                .eq(AiChatbotChatRecord::getConversationId, conversationId)
                .orderByAsc(AiChatbotChatRecord::getCreateTime)));
    }
}
