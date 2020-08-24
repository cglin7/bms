package org.zero.base.enums;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 异常类型类型枚举
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/1 10:52
 **/
@ToString
public enum ExceptionEnum {

    /**
     * 系统异常
     **/
    SERVER_ERROR(1, "系统异常"),

    /**
     * 运行时异常
     **/
    RUNTIME_ERROR(2, "运行时异常"),

    /**
     * 业务异常(未知具体业务异常时使用，但尽量使用已知类型的)
     **/
    BIZ_LOGIC_ERROR(3, "业务异常"),

    /**
     * 网络连接超时
     **/
    NETWORK_TIME_OUT(4, "网络连接超时"),

    /**
     * 功能服务不可用
     **/
    NETFLIX_CLIENT_EXCEPTION(51, "服务不可用,请等待5分钟后再试"),

    /**
     * 服务接口没找到
     **/
    FEIGN_EXCEPTION_NOT_FOUND(52, "服务接口没找到"),

    /**
     * 服务接口系统异常
     **/
    FEIGN_EXCEPTION_INTERNAL_SERVER_ERROR(53, "服务接口系统异常"),

    /**
     * 无效刷新令牌
     **/
    INVALID_REFRESH_TOKEN(6, "无效刷新令牌"),

    /************************************* 请求或响应异常（1xx） *************************************/
    /**
     * 参数类型转换错误
     **/
    PARAM_TYPE_MISMATCH(101, "参数类型不匹配，%s必须为%s类型"),

    /**
     * 参数缺失
     **/
    PARAM_NOT_FOUND(102, "参数缺失，请检查输入参数：%s"),

    /**
     * 非法参数
     **/
    ILLEGAL_ARGUMENT(103, "非法参数"),

    /**
     * 参数类型转换错误
     **/
    PARAM_DESERIALIZE_ERROR(104, "参数序列化错误"),

    /**
     * 参数类型转换错误
     **/
    PARAM_CONVERT_ERROR(105, "参数类型转换错误"),

    /************************************* 配置项异常（2xx） *************************************/
    /**
     * 租户小程序配置不存在
     **/
    TENANT_APP_CONFIG_NOT_EXIST(201, "租户小程序配置不存在"),

    /************************************* 数据库异常（10xx） *************************************/
    /**
     * 当前用户无权限访问该资源
     **/
    NO_PERMISSION(1001, "当前用户无权限访问该资源"),

    /**
     * 数据不存在或已被删除
     **/
    DATA_NOT_FOUND(1002, "数据不存在或已被删除"),

    /**
     * 数据完整性异常
     **/
    CONSTRAINT_VIOLATION(1003, "数据完整性异常"),

    /**
     * 乐观锁异常
     **/
    OP_LOCK_FAILURE(1004, "更新失败，数据已被他人修改"),

    /************************************* 用户认证异常（20xx） *************************************/

    /**
     * 未登录
     **/
    NOT_LOGIN(2001, "请先登录"),

    /**
     * 无效token
     **/
    INVALID_TOKEN(2002, "无效token"),

    /**
     * 用户不存在
     **/
    USER_NOT_EXIST(2003, "用户不存在"),

    /**
     * 微信openid为空
     **/
    WX_OPEN_ID_EMPTY(2004, "微信openid为空"),

    /**
     * 用户已禁用
     **/
    DISABLE_USER(2005, "用户已禁用"),

    /**
     * 用户token异常
     **/
    USER_TOKEN_ERROR(2006, "用户token异常"),

    /**
     * 用户信息异常
     **/
    USER_INFO_ERROR(2007, "用户信息异常"),

    /**
     * 获取用户信息失败
     **/
    GET_USER_INFO_FAILURE(2008, "获取用户信息失败"),

    /**
     * 登录平台异常
     **/
    INVALID_PLATFORM(2009, "登录平台异常"),

    /**
     * 用户名或密码错误
     **/
    WRONG_USER_OR_PWD(2010, "用户名或密码错误"),

    /************************************* 其它 *************************************/

    ;

    /**
     * 错误代码
     **/
    @Getter
    @Setter
    private int code;

    /**
     * 错误信息
     **/
    @Getter
    @Setter
    private String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
