package cn.yzdoit.purepanel.config;

import cn.yzdoit.purepanel.ai.AITools;
import com.alibaba.cloud.ai.memory.jdbc.MysqlChatMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * AI 配置
 *
 * @author 闫政
 * @since 2025/8/18 16:41 星期一
 */
@Configuration
@RequiredArgsConstructor
public class AIConfig {

    private final AITools aiTools;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        ChatMemoryRepository chatMemoryRepository = MysqlChatMemoryRepository.mysqlBuilder()
                .jdbcTemplate(jdbcTemplate)
                .build();
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(5)
                .build();
        return chatClientBuilder.defaultSystem("你是一个博学的智能聊天助手，请根据用户提问回答！")
                .defaultTools(aiTools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()
                        , new SimpleLoggerAdvisor())
                .build();
    }
}
