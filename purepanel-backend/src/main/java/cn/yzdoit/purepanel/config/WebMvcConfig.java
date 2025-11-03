package cn.yzdoit.purepanel.config;

import cn.yzdoit.purepanel.interceptor.LoginCodeInterceptor;
import cn.yzdoit.purepanel.pojo.properties.PurepanelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 闫政
 * @since 2025/6/25 06:59 星期三
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final StringRedisTemplate redisTemplate;
    private final PurepanelProperties purepanelProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCodeInterceptor(redisTemplate, purepanelProperties))
                //拦截的路径
                .addPathPatterns("/**")
                //排除的路径
                .excludePathPatterns("/login/**"
                        , "/swagger-ui/**"
                        , "/v3/api-docs/**"
                        , "/files/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + purepanelProperties.getLocalObjectStoreBasePath());
    }
}
