package cn.yzdoit.purepanel.exception;

import cn.yzdoit.purepanel.constant.enums.ApiStatusEnum;
import cn.yzdoit.purepanel.pojo.properties.PurepanelProperties;
import cn.yzdoit.purepanel.pojo.res.Res;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;


/**
 * 异常处理
 *
 * @author 闫政
 * @since 2025/6/23 06:27 星期一
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final PurepanelProperties purepanelProperties;

    /**
     * 处理其他所有异常
     *
     * @param req 请求参数
     * @param e   异常信息
     * @return Res
     */
    @ExceptionHandler(value = Exception.class)
    public Res<?> exceptionHandler(HttpServletRequest req, Exception e) {
        String msg;
        if (ObjectUtils.isNotEmpty(e.getCause())) {
            msg = e.getCause().getMessage();
        } else {
            msg = e.getMessage();
        }
        log.error("接口: {} 发生未知异常: {}", req.getRequestURI(), msg);
        log.error("未知异常:", e);
        if (purepanelProperties.getResExceptionEnabled()) {
            return Res.exception(msg);
        }
        return Res.fail();
    }


    /**
     * 校验参数异常
     *
     * @param req HttpServletRequest
     * @param e   异常信息
     * @return Res
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Res<?> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder msg = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            msg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append("，");
        }
        msg.replace(msg.length() - 1, msg.length(), "");
        log.error("接口: {} 请求参数校验不通过: {}", req.getRequestURI(), msg);
        return Res.fail(msg.toString());
    }

    /**
     * 处理业务异常
     *
     * @param req 请求参数
     * @param e   异常信息
     * @return Res
     */
    @ExceptionHandler(value = BusinessException.class)
    public Res<?> businessExceptionHandler(HttpServletRequest req, BusinessException e) {
        log.error("接口: {} 发生业务性异常: {}", req.getRequestURI(), e.getMessage());
        log.error("业务性异常:", e);
        if (e.getResIsException() && purepanelProperties.getResExceptionEnabled()) {
            return Res.exception(e.getMessage());
        }
        return Res.build(e.getStatus(), e.getMessage());
    }

    /**
     * 处理空指针异常
     *
     * @param req 请求参数
     * @param e   异常信息
     * @return Res
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Res<?> nullPointerExceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("接口: {} 发生空指针异常: {}", req.getRequestURI(), e.getStackTrace()[0]);
        log.error("空指针异常:", e);
        if (purepanelProperties.getResExceptionEnabled()) {
            return Res.exception("发生空指针异常:" + e.getStackTrace()[0]);
        }
        return Res.fail();
    }

    /**
     * 请求消息无法解析异常
     *
     * @param req 请求参数
     * @param e   异常信息
     * @return Res
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Res<?> httpMessageNotReadableExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException e) {
        log.error("接口: {} 发生请求消息无法解析异常: {}", req.getRequestURI(), e.getMessage());
        log.error("请求消息无法解析异常:", e);
        if (purepanelProperties.getResExceptionEnabled()) {
            return Res.exception("请检查请求体格式");
        }
        return Res.fail();
    }

    /**
     * 资源未找到异常
     *
     * @param req 请求参数
     * @param e   异常信息
     * @return Res
     */
    @ExceptionHandler(value = NoResourceFoundException.class)
    public Res<?> notFoundExceptionHandler(HttpServletRequest req, NoResourceFoundException e) {
        log.error("接口: {} 发生资源未找到异常: {}", req.getRequestURI(), e.getMessage());
        log.error("资源未找到异常:", e);
        return Res.build(ApiStatusEnum.NOT_FOUND);
    }

}