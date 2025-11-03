package cn.yzdoit.purepanel.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * 自定义MyBatis日志，解决logback文件中无法打印sql日志的问题
 *
 * @author 闫政
 * @since 2025/2/24 07:35 星期一
 */
@Slf4j
public class MyBatisLogImpl implements Log {

    public MyBatisLogImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
}
