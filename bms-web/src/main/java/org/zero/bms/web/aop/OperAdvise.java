package org.zero.bms.web.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zero.base.annotation.OperLog;
import org.zero.base.enums.ExceptionEnum;
import org.zero.base.exception.BusinessException;
import org.zero.bms.entity.dto.UserOperLogDTO;
import org.zero.bms.entity.po.User;
import org.zero.bms.service.UserOperLogService;
import org.zero.util.NetUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
  * 记录操作日志切面
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/21 17:55
  **/
@Slf4j
@Component
@Aspect
public class OperAdvise {

    @Autowired
    private UserOperLogService userOperLogService;

    @Pointcut("@annotation(org.zero.base.annotation.OperLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new BusinessException(ExceptionEnum.NOT_LOGIN);
        }

        //保存日志
        UserOperLogDTO dto = new UserOperLogDTO();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        OperLog operLog  = method.getAnnotation(OperLog .class);
        if (operLog != null) {
            String value = operLog.value();
            String type = operLog.type();
            dto.setOperation(value);//保存获取的操作
            dto.setType(type);
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        dto.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        if(params.length()>1000){
            dto.setParams("参数内容过多");
        }else{
            dto.setParams(params);
        }


        //获取用户名
        dto.setUsername(user.getRealname());

        //获取用户ID
        dto.setUserId(user.getId());

        dto.setIp(NetUtil.getIpAddress(request));

        //调用service保存SysLog实体类到数据库
        userOperLogService.add(dto);
    }
}
