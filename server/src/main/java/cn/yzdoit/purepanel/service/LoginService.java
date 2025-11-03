package cn.yzdoit.purepanel.service;

import cn.yzdoit.purepanel.pojo.req.AccountLoginReq;
import cn.yzdoit.purepanel.pojo.res.AccountLoginRes;
import cn.yzdoit.purepanel.pojo.res.GetCaptchaRes;

/**
 * 登录相关服务
 *
 * @author 闫政
 * @since 2025/6/23 06:58 星期一
 */
public interface LoginService {

    /**
     * 获取验证码
     *
     * @return GetCaptchaRes
     */
    GetCaptchaRes getCaptcha();


    /**
     * 账号登录
     *
     * @param req 请求参数
     * @return AccountLoginRes
     */
    AccountLoginRes accountLogin(AccountLoginReq req);
}
