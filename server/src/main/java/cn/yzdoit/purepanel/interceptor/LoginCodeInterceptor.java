package cn.yzdoit.purepanel.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.yzdoit.purepanel.constant.RedisPrefix;
import cn.yzdoit.purepanel.constant.enums.ApiStatusEnum;
import cn.yzdoit.purepanel.exception.BusinessException;
import cn.yzdoit.purepanel.pojo.entity.SysUser;
import cn.yzdoit.purepanel.pojo.properties.PurepanelProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * LoginCode 验证解析拦截器
 *
 * @author 闫政
 * @since 2025/6/25 06:56 星期三
 */
@Slf4j
@RequiredArgsConstructor
public class LoginCodeInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;
    private final PurepanelProperties purepanelProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String loginCode = request.getHeader("Login-Code");
        String loginStateCacheStr = redisTemplate.opsForValue().get(RedisPrefix.SYS_LOGIN_STATE + loginCode);
        if (StrUtil.isBlank(loginStateCacheStr)) {
            throw new BusinessException(ApiStatusEnum.UNAUTHORIZED);
        }
        SysUser sysUser = JSONUtil.toBean(loginStateCacheStr, SysUser.class);
        //校验单会话登录
        if (purepanelProperties.getLoginConfig().getSingleSessionLoginEnabled()) {
            String latestLoginCode = redisTemplate.opsForValue().get(RedisPrefix.SYS_LATEST_LOGIN_CODE + sysUser.getId());
            if (!StrUtil.equals(loginCode, latestLoginCode)) {
                throw new BusinessException(ApiStatusEnum.UNAUTHORIZED);
            }
        }
        request.setAttribute("loginUserId", sysUser.getId());
        request.setAttribute("loginUserAccount", sysUser.getAccount());
        return true;
    }
}
