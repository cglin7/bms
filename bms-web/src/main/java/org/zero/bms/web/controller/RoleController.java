package org.zero.bms.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zero.base.annotation.OperLog;
import org.zero.base.response.Result;
import org.zero.base.service.BaseService;
import org.zero.bms.entity.dto.RoleDTO;
import org.zero.bms.entity.dto.RoleInsertDTO;
import org.zero.bms.entity.dto.RoleUpdateDTO;
import org.zero.bms.entity.dto.UpdateRolePermissionsDTO;
import org.zero.bms.service.RoleService;

import javax.validation.Valid;

/**
 * 角色（Role）控制层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Api(tags = "角色")
@RequestMapping("/role")
@RestController
public class RoleController extends BaseService {
    @Autowired
    RoleService roleService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据")
    @GetMapping(value = "/getById")
    public Result findById(@RequestParam(value = "id") Long id) throws Exception {
        return Result.ok(roleService.getById(id));
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询列表")
    @GetMapping(value = "/getList")
    public Result getList(RoleDTO dto) throws Exception {
        return Result.ok(roleService.getList(dto));
    }

    /**
     * 分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询分页列表")
    @GetMapping(value = "/getPage")
    public Result getPage(RoleDTO dto) throws Exception {
        return Result.ok(roleService.getPage(dto));
    }

    /**
     * 新增
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增角色")
    @OperLog(value = "新增角色", type = "数据变更")
    @RequiresPermissions("/role/add")
    @PostMapping(value = "/add")
    public Result add(@Valid @RequestBody RoleInsertDTO dto) throws Exception {
        roleService.add(dto);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改角色")
    @OperLog(value = "修改角色", type = "数据变更")
    @RequiresPermissions("/role/update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody RoleUpdateDTO dto) throws Exception {
        roleService.update(dto);
        return Result.ok();
    }

    /**
     * 通过主键删除数据
     *
     * @param dto 数据传输对象
     */
    @ApiOperation(value = "删除角色")
    @OperLog(value = "删除角色", type = "数据变更")
    @RequiresPermissions("/role/deleteById")
    @PostMapping(value = "/deleteById")
    public Result deleteById(@Valid @RequestBody RoleUpdateDTO dto) throws Exception {
        roleService.deleteById(dto);
        return Result.ok();
    }

    /**
     * 设置角色权限
     *
     * @param dto 数据传输对象
     */
    @ApiOperation(value = "设置角色权限")
    @OperLog(value = "设置角色权限", type = "数据变更")
    @RequiresPermissions("/role/setRolePermissions")
    @PostMapping(value = "/setRolePermissions")
    public Result setRolePermissions(@Valid @RequestBody UpdateRolePermissionsDTO dto) throws Exception {
        roleService.setRolePermissions(dto);
        return Result.ok();
    }

}
