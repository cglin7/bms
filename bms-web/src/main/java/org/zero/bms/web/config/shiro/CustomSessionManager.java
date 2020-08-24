package org.zero.bms.web.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.UUID;

/**
 * 自定义shiro会话管理器
 * 对于app或小程序端无登录状态访问时，通过客户端的token来创建会话
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/1 10:55
 **/
@Slf4j
public class CustomSessionManager extends DefaultWebSessionManager {
    /**
     * 这个是客户端请求给服务端带的header
     */
    public final static String HEADER_TOKEN_NAME = "token";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /**
     * 重写getSessionId,分析请求头中的指定参数，做用户凭证sessionId
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(HEADER_TOKEN_NAME);
        if (StringUtils.isEmpty(id)) {
            //如果未携带token则直接使用父类的方法
            Serializable newId = super.getSessionId(request, response);
            if (newId == null) {
                newId = UUID.randomUUID().toString();
            }
            ((HttpServletResponse) response).setHeader("token", newId.toString());
            return newId;
        } else {
            //如果请求头中有 authToken 则其值为sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            ((HttpServletResponse) response).setHeader("token", id);
            return id;
        }
    }

}

