package org.zero.bms.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zero.base.annotation.OperLog;
import org.zero.base.response.Result;
import org.zero.base.service.BaseService;
import org.zero.bms.entity.dto.*;
import org.zero.bms.entity.po.User;
import org.zero.bms.service.UserService;

import javax.validation.Valid;

/**
 * 用户（User）控制层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Api(tags = "用户")
@RequestMapping("/user")
@RestController
public class UserController extends BaseService {
    @Autowired
    UserService userService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据")
    @GetMapping(value = "/getById")
    public Result findById(@RequestParam(value = "id") Long id) throws Exception {
        return Result.ok(userService.getById(id));
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询列表")
    @GetMapping(value = "/getList")
    public Result getList(UserDTO dto) throws Exception {
        return Result.ok(userService.getList(dto));
    }

    /**
     * 分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询分页列表")
    @GetMapping(value = "/getPage")
    public Result getPage(UserDTO dto) throws Exception {
        return Result.ok(userService.getPage(dto));
    }

    /**
     * 修改
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改用户")
    @OperLog(value = "修改用户", type = "数据变更")
    @RequiresPermissions("/user/update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody UserUpdateDTO dto) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (!user.getId().equals(dto.getId())) {
            return Result.fail("当前用户无权修改其他用户的数据");
        }
        userService.update(dto);
        return Result.ok();
    }

    /**
     * 设置用户角色
     *
     * @param dto:
     * @return org.zero.base.response.Result:
     * @author : cgl
     * @version : 1.0
     * @since 2020/8/20 18:57
     **/
    @ApiOperation(value = "设置用户角色")
    @OperLog(value = "设置用户角色", type = "数据变更")
    @RequiresPermissions("/user/setUserRoles")
    @PostMapping(value = "/setUserRoles")
    public Result setUserRoles(@Valid @RequestBody UpdateUserRolesDTO dto) throws Exception {
        userService.setUserRoles(dto);
        return Result.ok();
    }

}
