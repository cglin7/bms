package org.zero.bms.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zero.base.annotation.OperLog;
import org.zero.base.response.Result;
import org.zero.base.service.BaseService;
import org.zero.bms.entity.dto.CategoryDTO;
import org.zero.bms.entity.dto.CategoryInsertDTO;
import org.zero.bms.entity.dto.CategoryUpdateDTO;
import org.zero.bms.entity.po.Category;
import org.zero.bms.service.CategoryService;
import org.zero.util.tree.Tree;

import javax.validation.Valid;
import java.util.List;

/**
 * 分类（Category）控制层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Api(tags = "分类")
@RequestMapping("/category")
@RestController
public class CategoryController extends BaseService {
    @Autowired
    CategoryService categoryService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据")
    @GetMapping(value = "/getById")
    public Result findById(@RequestParam(value = "id") Long id) throws Exception {
        return Result.ok(categoryService.getById(id));
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询列表")
    @GetMapping(value = "/getList")
    public Result getList(CategoryDTO dto) throws Exception {
        return Result.ok(categoryService.getList(dto));
    }

    /**
     * 分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询分页列表")
    @GetMapping(value = "/getPage")
    public Result getPage(CategoryDTO dto) throws Exception {
        return Result.ok(categoryService.getPage(dto));
    }

    /**
     * 新增
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增分类")
    @OperLog(value = "新增分类", type = "数据变更")
    @RequiresPermissions("/category/add")
    @PostMapping(value = "/add")
    public Result add(@Valid @RequestBody CategoryInsertDTO dto) throws Exception {
        categoryService.add(dto);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改分类")
    @OperLog(value = "修改分类", type = "数据变更")
    @RequiresPermissions("/category/update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody CategoryUpdateDTO dto) throws Exception {
        categoryService.update(dto);
        return Result.ok();
    }

    /**
     * 通过主键删除数据
     *
     * @param dto 数据传输对象
     */
    @ApiOperation(value = "删除分类")
    @OperLog(value = "删除分类", type = "数据变更")
    @RequiresPermissions("/category/deleteById")
    @PostMapping(value = "/deleteById")
    public Result deleteById(@Valid @RequestBody CategoryUpdateDTO dto) throws Exception {
        categoryService.deleteById(dto);
        return Result.ok();
    }

    /**
     * 查询树型列表
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询树型列表")
    @GetMapping(value = "/getTree")
    public Result getTree(CategoryDTO dto) throws Exception {
        List<Category> list = categoryService.getList(dto);
        Tree tree = Tree.build(list);
        return Result.ok(tree.getRoot().getChildren());
    }

}
