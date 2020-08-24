package org.zero.bms.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zero.base.annotation.OperLog;
import org.zero.base.response.Result;
import org.zero.base.service.BaseService;
import org.zero.bms.entity.dto.BookDTO;
import org.zero.bms.entity.dto.BookInsertDTO;
import org.zero.bms.entity.dto.BookUpdateDTO;
import org.zero.bms.service.BookService;

import javax.validation.Valid;

/**
 * 图书（Book）控制层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Api(tags = "图书")
@RequestMapping("/book")
@RestController
public class BookController extends BaseService {
    @Autowired
    BookService bookService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据")
    @ApiImplicitParam(name = "id", value = "id", paramType = "query", required = true, dataType = "Long")
    @GetMapping(value = "/getById")
    public Result findById(@RequestParam(value = "id") Long id) throws Exception {
        return Result.ok(bookService.getById(id));
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询列表")
    @GetMapping(value = "/getList")
    public Result getList(BookDTO dto) throws Exception {
        return Result.ok(bookService.getList(dto));
    }

    /**
     * 分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    @ApiOperation(value = "查询分页列表")
    @GetMapping(value = "/getPage")
    public Result getPage(BookDTO dto) throws Exception {
        return Result.ok(bookService.getPage(dto));
    }

    /**
     * 新增
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增图书")
    @OperLog(value = "新增图书", type = "数据变更")
    @RequiresPermissions("/book/add")
    @PostMapping(value = "/add")
    public Result add(@Valid @RequestBody BookInsertDTO dto) throws Exception {
        bookService.add(dto);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @ApiOperation(value = "修改图书")
    @OperLog(value = "修改图书", type = "数据变更")
    @RequiresPermissions("/book/update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody BookUpdateDTO dto) throws Exception {
        bookService.update(dto);
        return Result.ok();
    }

    /**
     * 通过主键删除数据
     *
     * @param dto 数据传输对象
     */
    @ApiOperation(value = "删除图书")
    @OperLog(value = "删除图书", type = "数据变更")
    @RequiresPermissions("/book/deleteById")
    @PostMapping(value = "/deleteById")
    public Result deleteById(@Valid @RequestBody BookUpdateDTO dto) throws Exception {
        bookService.deleteById(dto);
        return Result.ok();
    }

}
