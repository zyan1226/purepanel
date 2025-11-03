package cn.yzdoit.purepanel.exception;

import cn.yzdoit.purepanel.constant.enums.ApiStatusEnum;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author 闫政
 * @since 2025/6/23 05:05 星期一
 */
@Getter
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5815930111330732899L;

    public BusinessException(ApiStatusEnum apiStatusEnum) {
        this.message = apiStatusEnum.getMsg();
        this.resIsException = false;
        this.status = apiStatusEnum.getStatus();
    }

    public BusinessException(String message) {
        this.message = message;
        this.resIsException = false;
        this.status = ApiStatusEnum.FAIL.getStatus();
    }

    public BusinessException(String message, Boolean resIsException) {
        this.message = message;
        this.resIsException = resIsException;
        this.status = ApiStatusEnum.FAIL.getStatus();
    }

    private final Boolean resIsException;
    private final String message;
    private final Integer status;
}
