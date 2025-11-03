package cn.yzdoit.purepanel.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.mapper.AiChatbotChatRecordMapper;
import cn.yzdoit.purepanel.mapper.AiChatbotConversationMapper;
import cn.yzdoit.purepanel.pojo.entity.AiChatbotChatRecord;
import cn.yzdoit.purepanel.pojo.entity.AiChatbotConversation;
import cn.yzdoit.purepanel.pojo.req.AIChatReq;
import cn.yzdoit.purepanel.service.AIChatService;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 聊天机器人相关服务
 *
 * @author 闫政
 * @since 2025/8/21 10:13 星期四
 */
@Service
@RequiredArgsConstructor
public class AIChatServiceImpl implements AIChatService {

    private final ChatClient chatClient;
    private final AiChatbotConversationMapper aiChatbotConversationMapper;
    private final AiChatbotChatRecordMapper aiChatbotChatRecordMapper;

    /**
     * AI 聊天
     *
     * @param req    请求参数
     * @param userId 用户ID
     * @return Flux 流式结果
     */
    @Override
    public Flux<String> chat(AIChatReq req, String userId) {
        String conversationId = req.getConversationId();
        String question = req.getQuestion();
        String modelName = req.getModelName();

        //处理会话
        Long count = aiChatbotConversationMapper.selectCount(Wrappers.<AiChatbotConversation>lambdaQuery()
                .eq(AiChatbotConversation::getId, conversationId));
        if (count == 0) {
            String conversationTitle = question;
            if (conversationTitle.length() > 64) {
                conversationTitle = question.substring(0, 32);
            }
            AiChatbotConversation aiChatbotConversation = AiChatbotConversation.builder()
                    .id(conversationId)
                    .title(conversationTitle)
                    .userId(userId)
                    .build();
            aiChatbotConversationMapper.insert(aiChatbotConversation);
        }
        //处理会话记录
        aiChatbotChatRecordMapper.insert(AiChatbotChatRecord.builder()
                .conversationId(conversationId)
                .type("USER")
                .content(question)
                .build());

        //支持的模型名称
        Set<String> supportModeNameSet = Arrays.stream(DashScopeApi.ChatModel.values())
                .map(DashScopeApi.ChatModel::getValue).collect(Collectors.toSet());

        //模型名称为空时，使用默认模型
        if (StrUtil.isBlank(modelName)) {
            modelName = DashScopeApi.ChatModel.QWEN_MAX.getName();
        } else if (!supportModeNameSet.contains(modelName)) {
            return Flux.just("不支持的模型");
        }

        //存储所有AI响应内容
        StringBuilder allAssistantContent = new StringBuilder();

        //调用并返回
        return chatClient.prompt()
                //调用选项
                .options(DashScopeChatOptions.builder()
                        .withModel(modelName)
                        .build())
                //增强工具
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                //用户输入
                .user(question)
                //流式返回
                .stream()
                .content()
                //每接收一段内容回调
                .doOnNext(allAssistantContent::append)
                //接收完成回调
                .doOnComplete(() -> aiChatbotChatRecordMapper.insert(AiChatbotChatRecord.builder()
                        .conversationId(conversationId)
                        .type("ASSISTANT")
                        .content(allAssistantContent.toString())
                        .build()));
    }
}
