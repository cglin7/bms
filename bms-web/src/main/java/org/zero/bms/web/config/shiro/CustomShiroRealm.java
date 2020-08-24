package org.zero.bms.web.config.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.zero.bms.dao.UserRepository;
import org.zero.bms.entity.po.Permission;
import org.zero.bms.entity.po.Role;
import org.zero.bms.entity.po.User;
import org.zero.bms.service.enums.UserStatusEnum;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义realm域
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/1 11:06
 **/
public class CustomShiroRealm extends AuthorizingRealm {

    @Autowired
    UserRepository userRepository;

    /**
     * @param token
     * @return
     * @description 认证.登录
     * @author cgl
     * @date 2018/12/27 16:07
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        String username = utoken.getUsername();
        User user = userRepository.findByName(username);
        if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        } else if (null == user.getStatus()) {
            throw new DisabledAccountException("帐号状态异常！");
        } else if (user.getStatus() == UserStatusEnum.WAITING_AUDIT.getCode()) {
            throw new DisabledAccountException("帐号待审核！");
        } else if (user.getStatus() == UserStatusEnum.LOCKED.getCode()) {
            throw new DisabledAccountException("帐号已锁定！");
        }
        //放入shiro.调用CredentialsMatcher检验密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }


    /**
     * 授权
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/2/1 11:05
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        Object obj = principal.getPrimaryPrincipal();
        if (!(obj instanceof User)) {
            return null;
        }
        User user = (User) obj;
        Set<String> permissions = new HashSet<>();
        Set<Role> roles = user.getRoles();
        if (roles.size() > 0) {
            for (Role role : roles) {
                Set<Permission> perms = role.getPermissions();
                for (Permission permission : perms) {
                    permissions.add(permission.getUrl());
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permissions);
        info.setRoles(roles.stream().map(Role::getName).collect(Collectors.toSet()));
        return info;
    }

}
