package cn.yzdoit.purepanel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * swagger 配置
 *
 * @author 闫政
 * @since 2025/6/17 14:51 星期二
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("purepanel-backend")
                .description("")
                .version("0.0.1")
        );
    }
}
