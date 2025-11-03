package cn.yzdoit.purepanel.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.yzdoit.purepanel.exception.BusinessException;

/**
 * 条件检查工具类
 *
 * @author 闫政
 * @since 2025/6/23 19:40 周一
 */
public class CheckUtil {

    public static void equals(String textA, String textB, String msg) {
        check(!StrUtil.equals(textA, textB), msg);
    }

    public static void equalsIgnoreCase(String textA, String textB, String msg) {
        check(StrUtil.equalsIgnoreCase(textA, textB), msg);
    }

    public static void notEmpty(Object obj, String msg) {
        check(ObjectUtil.isNotEmpty(obj), msg);
    }

    public static void notNull(Object obj, String msg) {
        check(null != obj, msg);
    }

    public static void notBlank(String text, String msg) {
        check(StrUtil.isNotBlank(text), msg);
    }

    public static void check(boolean flag, String msg) {
        if (!flag) {
            throw new BusinessException(msg);
        }
    }
}
