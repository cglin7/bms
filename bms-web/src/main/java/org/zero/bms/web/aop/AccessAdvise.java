package org.zero.bms.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zero.util.NetUtil;

import javax.servlet.http.HttpServletRequest;

/**
  * 访问记录切面
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/21 17:51
  **/
@Slf4j
@Component
@Aspect
public class AccessAdvise {

    @Before("within(org.zero.bms.web.controller.*)")
    public void before(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("请求路径：{}，请求方式：{}，源ip：{}", request.getRequestURI(), request.getMethod(), NetUtil.getIpAddress(request));
    }
}
