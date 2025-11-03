package cn.yzdoit.purepanel.util;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.yzdoit.purepanel.pojo.bo.TwoParams;

/**
 * 密码工具类
 *
 * @author 闫政
 * @since 2025/6/23 18:27 周一
 */
public class PwdUtil {

    /**
     * md5编码
     *
     * @param pwd 密码明文
     * @return TwoParams a-密码 b-密码盐
     */
    public static TwoParams<String, String> encode(String pwd) {
        return encode(pwd, RandomUtil.randomString(8));
    }

    /**
     * 密码校验
     *
     * @param originalTextPwd 密码明文
     * @param salt            密码盐
     * @param encodedTextPwd  密码密文
     * @return boolean
     */
    public static boolean verify(String originalTextPwd, String salt, String encodedTextPwd) {
        return StrUtil.equals(encode(originalTextPwd, salt).getA(), encodedTextPwd);
    }

    /**
     * md5编码
     *
     * @param pwd  密码明文
     * @param salt 密码盐
     * @return TwoParams a-密码 b-密码盐
     */
    private static TwoParams<String, String> encode(String pwd, String salt) {
        String encodePassword = pwd + salt;
        for (int i = 0; i < 5; i++) {
            encodePassword = MD5.create().digestHex(encodePassword);
        }
        return TwoParams.<String, String>builder()
                .a(encodePassword)
                .b(salt)
                .build();
    }
}
