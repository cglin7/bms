package org.zero.base.exception;

import org.zero.base.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @author : cgl
 * @description : 自定义业务异常类
 * @date : 2019/1/2 9:09
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误编码
     */
    @Getter
    private int errorCode;

    public BusinessException(ExceptionEnum e) {
        super(e.getMsg());
        this.errorCode = e.getCode();
    }

    public BusinessException(String msg) {
        super(msg);
        this.errorCode = 2;
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.errorCode = code;
    }

}
