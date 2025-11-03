package cn.yzdoit.purepanel.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.yzdoit.purepanel.constant.RedisPrefix;
import cn.yzdoit.purepanel.mapper.SysUserMapper;
import cn.yzdoit.purepanel.pojo.entity.SysUser;
import cn.yzdoit.purepanel.pojo.properties.PurepanelProperties;
import cn.yzdoit.purepanel.pojo.req.AccountLoginReq;
import cn.yzdoit.purepanel.pojo.res.AccountLoginRes;
import cn.yzdoit.purepanel.pojo.res.GetCaptchaRes;
import cn.yzdoit.purepanel.service.LoginService;
import cn.yzdoit.purepanel.util.CheckUtil;
import cn.yzdoit.purepanel.util.PwdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wf.captcha.SpecCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 登录相关服务
 *
 * @author 闫政
 * @since 2025/6/23 06:58 星期一
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final StringRedisTemplate redisTemplate;
    private final SysUserMapper sysUserMapper;
    private final PurepanelProperties purepanelProperties;

    /**
     * 获取验证码
     *
     * @return GetCaptchaRes
     */
    @Override
    public GetCaptchaRes getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(140, 48, 4);
        String captchaKey = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(RedisPrefix.SYS_CAPTCHA + captchaKey, specCaptcha.text(), 1, TimeUnit.MINUTES);
        return GetCaptchaRes.builder()
                .captchaBase64(specCaptcha.toBase64())
                .captchaKey(captchaKey)
                .build();
    }

    /**
     * 账号登录
     *
     * @param req 请求参数
     * @return AccountLoginRes
     */
    @Override
    public AccountLoginRes accountLogin(AccountLoginReq req) {
        //前置校验
        String captchaCache = redisTemplate.opsForValue().get(RedisPrefix.SYS_CAPTCHA + req.getCaptchaKey());
        CheckUtil.notBlank(captchaCache, "验证码已过期，请点击验证码重新获取");
        CheckUtil.equalsIgnoreCase(captchaCache, req.getCaptcha(), "验证码错误");
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getId, SysUser::getName, SysUser::getAccount, SysUser::getAvatar
                        , SysUser::getStatus, SysUser::getSalt, SysUser::getPwd)
                .eq(SysUser::getAccount, req.getAccount()));
        CheckUtil.notNull(sysUser, "账号或密码错误");
        CheckUtil.check(sysUser.getStatus(), "该账号已被禁用");
        CheckUtil.check(PwdUtil.verify(req.getPwd(), sysUser.getSalt(), sysUser.getPwd()), "账号或密码错误");
        //移除非必要字段
        sysUser.setPwd(null);
        sysUser.setSalt(null);
        sysUser.setStatus(null);
        //生成登录码
        String loginCode = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(RedisPrefix.SYS_LOGIN_STATE + loginCode, JSONUtil.toJsonStr(sysUser)
                , purepanelProperties.getLoginConfig().getLoginStatusExpireTime(), TimeUnit.HOURS);
        redisTemplate.opsForValue().set(RedisPrefix.SYS_LATEST_LOGIN_CODE + sysUser.getId(), loginCode
                , purepanelProperties.getLoginConfig().getLoginStatusExpireTime(), TimeUnit.HOURS);
        return AccountLoginRes.builder()
                .loginCode(loginCode)
                .sysUser(sysUser)
                .build();
    }
}
