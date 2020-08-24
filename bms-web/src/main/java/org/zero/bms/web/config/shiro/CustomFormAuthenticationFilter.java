package org.zero.bms.web.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.zero.base.enums.ExceptionEnum;
import org.zero.base.response.Result;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 自定义认证过滤器
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/1 10:46
 **/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter
{
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        Result result = new Result(ExceptionEnum.NOT_LOGIN.getCode(), ExceptionEnum.NOT_LOGIN.getMsg());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(result));
        return false;
    }
}
