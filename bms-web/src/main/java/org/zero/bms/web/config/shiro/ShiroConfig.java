package org.zero.bms.web.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/1 10:59
 **/
@Configuration
public class ShiroConfig {

    /**
     * 开发环境标识
     */
    @Value("${env.dev:false}")
    private boolean isDev;

    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") CustomShiroRealm authRealm,
                                           @Qualifier("sessionManager") SessionManager sessionManager) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 设置realm.
        manager.setRealm(authRealm);
        // 自定义session管理
        manager.setSessionManager(sessionManager);
        return manager;
    }

    //配置自定义的权限登录器
    @Bean(name = "authRealm")
    public CustomShiroRealm authRealm(@Qualifier("credentialsMatcher") CustomCredentialsMatcher matcher) {
        CustomShiroRealm authRealm = new CustomShiroRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    //配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CustomCredentialsMatcher credentialsMatcher() {
        return new CustomCredentialsMatcher();
    }

    // 默认会话管理器
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(18000000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    /**
     * @param :
     * @return :
     * @description : 无状态会话管理器
     * @author : cgl
     * @date : 2019/1/18 8:50
     **/
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setGlobalSessionTimeout(3600000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.getFilters().put("authc", new CustomFormAuthenticationFilter()); // 增加自定义过滤器，未登录状态下不重定向至登录页，而是返回code -2001 进行提示

        // 必须设置 SecurityManager
        bean.setSecurityManager(securityManager);

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //  authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        // 配置不会被拦截的链接 顺序判断 一般将 /**放在最为下边
        // 除了登录请求可以匿名访问，其它请求都需要登录才能访问
        if (!isDev) {
            filterChainDefinitionMap.put("/hello", "anon");
            filterChainDefinitionMap.put("/h2-console/**", "anon");
            filterChainDefinitionMap.put("/swagger-ui.html", "anon");
            filterChainDefinitionMap.put("/webjars/**", "anon");
            filterChainDefinitionMap.put("/v2/**", "anon");
            filterChainDefinitionMap.put("/swagger-resources/**", "anon");
            filterChainDefinitionMap.put("/login", "anon");
            filterChainDefinitionMap.put("/logout", "anon");
            filterChainDefinitionMap.put("/register", "anon");
            filterChainDefinitionMap.put("/**", "authc");
        }

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

}
