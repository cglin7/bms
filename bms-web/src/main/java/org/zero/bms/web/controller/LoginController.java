package org.zero.bms.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zero.base.enums.ExceptionEnum;
import org.zero.base.exception.BusinessException;
import org.zero.base.response.Result;
import org.zero.bms.entity.dto.LoginDTO;
import org.zero.bms.entity.dto.UserInsertDTO;
import org.zero.bms.entity.po.User;
import org.zero.bms.service.UserService;

import javax.validation.Valid;

@Api(tags = "登录/退出/注册")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * @param :
     * @return :
     * @description : 未登录时，返回提示信息（url在ShiroConfig里配置）
     * @author : cgl
     * @date : 2019/1/9 14:45
     **/
    @GetMapping(value = "/toLogin")
    public Result toLogin() {
        throw new BusinessException(ExceptionEnum.NOT_LOGIN);
    }

    /**
     * @param dto : 用户名及密码
     * @return :
     * @description : 登录
     * @author : cgl
     * @date : 2019/1/9 14:36
     **/
    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginDTO dto) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(dto.getName(), dto.getPassword());
            //login方法提交认证，会执行CustomerShiroRealm中的doGetAuthenticationInfo认证方法
            SecurityUtils.getSubject().login(token);
            User admin = (User) SecurityUtils.getSubject().getPrincipal();
            return Result.ok(admin);
        } catch (DisabledAccountException e) {
            return Result.fail(ExceptionEnum.DISABLE_USER);
        } catch (AuthenticationException e) {
            return Result.fail(ExceptionEnum.WRONG_USER_OR_PWD);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @return :
     * @description : 退出
     * @author : cgl
     * @date : 2019/1/9 14:37
     **/
    @ApiOperation(value = "退出")
    @GetMapping(value = "logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    /**
     * 用户注册
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/4/17 10:32
     **/
    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public Result register(@Valid @RequestBody UserInsertDTO dto) throws Exception {
        userService.add(dto);
        return Result.ok();
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }
}
