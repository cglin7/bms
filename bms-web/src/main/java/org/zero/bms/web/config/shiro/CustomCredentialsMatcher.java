package org.zero.bms.web.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.zero.util.CryptoUtil;

/**
 * 自定义密码校验器
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/1 12:06
 **/
@Slf4j
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;

        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(utoken.getPassword());
        //简单加密：密码+用户名 做MD5加密
        String encryptPassword = CryptoUtil.getMD5(inPassword).toUpperCase();
        //获得数据库中的密码
        String dbPassword = ((String) info.getCredentials()).toUpperCase();
        //进行密码的比对
        boolean compare = this.equals(encryptPassword, dbPassword);
        log.info("用户【" + utoken.getUsername() + "】登录：" + (compare == true ? "成功" : "失败"));
        return compare;
    }

}
