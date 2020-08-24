package org.zero.bms.web.exceptionhandler;

import org.zero.base.enums.ExceptionEnum;
import org.zero.base.exception.BusinessException;
import org.zero.base.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * 控制层统一异常处理
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/1 15:48
 **/
@RestControllerAdvice
public class GlobalExceptionHandleAdvice {
    Logger bizLog = LoggerFactory.getLogger("business");
    Logger sysErrLog = LoggerFactory.getLogger("exception");

    /**
     * 处理业务异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:32
     **/
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        bizLog.error(e.getMessage());
        return Result.fail(e.getMessage());
    }

    /**
     * 处理访问方式异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:05
     **/
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.fail("不支持以当前方式访问：" + e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/2/2 16:40
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleBindException(MethodArgumentNotValidException e) {
        return Result.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 处理参数类型转换异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:05
     **/
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public Result handleParamConvertException(Exception e) {
        sysErrLog.error(e.getMessage());
        String property = e.getMessage().substring(e.getMessage().indexOf("[\"") + 2, e.getMessage().indexOf("\"]"));
        return Result.fail(ExceptionEnum.PARAM_CONVERT_ERROR.getMsg() + "[" + property + "]");
    }

    /**
     * 处理参数缺失异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:05
     **/
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Result handleMissingServletRequestParameterException(Exception e) {
        sysErrLog.error(e.getMessage());
        return Result.fail(ExceptionEnum.PARAM_NOT_FOUND);
    }

    /**
     * 处理数据库异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:37
     **/
    @ExceptionHandler({EmptyResultDataAccessException.class, DataIntegrityViolationException.class, ObjectOptimisticLockingFailureException.class})
    public Result handleDataException(Exception e) {
        Result result = null;
        if (e instanceof EmptyResultDataAccessException) { // 数据不存在或已删除
            result = Result.fail(ExceptionEnum.DATA_NOT_FOUND);
        } else if (e instanceof DataIntegrityViolationException) { // 数据完整性异常
            result = Result.fail(ExceptionEnum.SERVER_ERROR);
            if (e.getMessage().contains("ConstraintViolationException")) { // 主键或索引冲突
                result = Result.fail(ExceptionEnum.CONSTRAINT_VIOLATION);
            }
        } else if (e instanceof ObjectOptimisticLockingFailureException) { // 数据库乐观锁异常
            result = Result.fail(ExceptionEnum.OP_LOCK_FAILURE);
        }
        return result;
    }

    /**
     * 处理网络连接超时异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:24
     **/
    @ExceptionHandler({ConnectException.class})
    public Result handleConnectionException(Exception e) {
        return Result.fail(ExceptionEnum.NETWORK_TIME_OUT);
    }

    /**
     * 查询实体异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:24
     **/
    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public Result handleEntityNotFoundException(JpaObjectRetrievalFailureException e) {
        String msg = e.getMessage();
        int index = msg.lastIndexOf("with id ");
        String entity = msg.substring(msg.lastIndexOf(".") + 1, index);
        String id = msg.substring(index + 8);
        return Result.fail(entity + "[" + id + "] 可能已被删除");
    }

    /**
     * 处理其它异常
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/1/26 17:24
     **/
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        if (isShiroException(e)) {
            return Result.fail(ExceptionEnum.NO_PERMISSION);
        }

        e.printStackTrace(); // 开发时打印至控制台
        return Result.fail(ExceptionEnum.SERVER_ERROR);
    }

    private boolean isShiroException(Exception e) {
        List<String> exceptions = new ArrayList<>();
        exceptions.add("org.apache.shiro.authz.AuthorizationException");
        exceptions.add("org.apache.shiro.authz.UnauthenticatedException");
        exceptions.add("org.apache.shiro.authz.UnauthorizedException");

        if (exceptions.contains(e.getClass().getName())) {
            return true;
        }
        return false;
    }

}
