package org.zero.base.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.zero.base.enums.ExceptionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 异常信息
     */
    private String error;
    /**
     * 返回内容
     */
    private T content;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public Result(int code, String msg, String error)
    {
        this.code = code;
        this.msg = msg;
        this.error = error;
    }

    public Result(int code, String msg, String error, T content) {
        this.code = code;
        this.msg = msg;
        this.error = error;
        this.content = content;
    }

    public static Result ok() {
        return new Result(0, "成功");
    }

    public static <T> Result ok(T content) {
        return new Result(0, "成功", "", content);
    }

    public static Result fail(String message) {
        return new Result(1, message);
    }

    public static Result fail(ExceptionEnum enums) {
        return new Result(enums.getCode(), enums.getMsg());
    }

    public static Result fail(int code, String message) {
        return new Result(code, message);
    }

    public static Result fail(int code, String message, String error) {
        return new Result(code, message, error);
    }

}
