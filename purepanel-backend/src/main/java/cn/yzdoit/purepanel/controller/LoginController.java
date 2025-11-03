package cn.yzdoit.purepanel.controller;

import cn.yzdoit.purepanel.pojo.req.AccountLoginReq;
import cn.yzdoit.purepanel.pojo.res.AccountLoginRes;
import cn.yzdoit.purepanel.pojo.res.GetCaptchaRes;
import cn.yzdoit.purepanel.pojo.res.Res;
import cn.yzdoit.purepanel.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 登录相关接口
 *
 * @author 闫政
 * @since 2025/6/23 06:57 星期一
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "登录相关接口")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/getCaptcha")
    @Operation(summary = "获取验证码")
    public Res<GetCaptchaRes> getCaptcha() {
        return Res.success(loginService.getCaptcha());
    }

    @PostMapping("/accountLogin")
    @Operation(summary = "账号登录")
    public Res<AccountLoginRes> accountLogin(@RequestBody @Validated AccountLoginReq req) {
        return Res.success(loginService.accountLogin(req));
    }
}
