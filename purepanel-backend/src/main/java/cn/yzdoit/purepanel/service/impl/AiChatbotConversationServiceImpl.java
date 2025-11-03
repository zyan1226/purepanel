package cn.yzdoit.purepanel.service.impl;


import cn.yzdoit.purepanel.mapper.AiChatbotConversationMapper;
import cn.yzdoit.purepanel.pojo.entity.AiChatbotConversation;
import cn.yzdoit.purepanel.service.AiChatbotConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * AI 聊天会话相关服务
 *
 * @author 闫政
 * @since 2025/8/22 22:46 周五
 */
@Service
public class AiChatbotConversationServiceImpl extends ServiceImpl<AiChatbotConversationMapper, AiChatbotConversation>
        implements AiChatbotConversationService {
}
