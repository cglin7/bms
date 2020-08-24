package org.zero.bms.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zero.base.annotation.OperLog;
import org.zero.base.response.Result;
import org.zero.base.service.BaseService;
import org.zero.bms.entity.dto.*;
import org.zero.bms.service.PermissionService;
import org.zero.bms.service.UserService;

import javax.validation.Valid;

/**
 * 权限（Permission）控制层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Api(tags = "权限")
@RequestMapping("/permission")
@RestController
public class PermissionController extends BaseService {
    @Autowired
    PermissionService permissionService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据")
    @GetMapping(value = "/getById")
    public Result findById(@RequestParam(value = "id") Long id) throws Exception {
        return Result.ok(permissionService.getById(id));
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询列表")
    @GetMapping(value = "/getList")
    public Result getList(PermissionDTO dto) throws Exception {
        return Result.ok(permissionService.getList(dto));
    }

    /**
     * 分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询分页列表")
    @GetMapping(value = "/getPage")
    public Result getPage(PermissionDTO dto) throws Exception {
        return Result.ok(permissionService.getPage(dto));
    }

    /**
     * 新增
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增权限")
    @OperLog(value = "新增权限", type = "数据变更")
    @RequiresPermissions("/permission/add")
    @PostMapping(value = "/add")
    public Result add(@Valid @RequestBody PermissionInsertDTO dto) throws Exception {
        permissionService.add(dto);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改权限")
    @OperLog(value = "修改权限", type = "数据变更")
    @RequiresPermissions("/permission/update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody PermissionUpdateDTO dto) throws Exception {
        permissionService.update(dto);
        return Result.ok();
    }

    /**
     * 通过主键删除数据
     *
     * @param dto 数据传输对象
     */
    @ApiOperation(value = "删除权限")
    @OperLog(value = "删除权限", type = "数据变更")
    @RequiresPermissions("/permission/deleteById")
    @PostMapping(value = "/deleteById")
    public Result deleteById(@Valid @RequestBody PermissionUpdateDTO dto) throws Exception {
        permissionService.deleteById(dto);
        return Result.ok();
    }

}
