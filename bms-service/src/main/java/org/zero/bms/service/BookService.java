package org.zero.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.zero.base.dto.PageDTO;
import org.zero.base.service.BaseService;
import org.zero.bms.dao.BookRepository;
import org.zero.bms.dao.CategoryRepository;
import org.zero.bms.entity.dto.BookDTO;
import org.zero.bms.entity.dto.BookInsertDTO;
import org.zero.bms.entity.dto.BookUpdateDTO;
import org.zero.bms.entity.po.Book;
import org.zero.bms.entity.po.Category;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 图书（Book）服务层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Service
public class BookService extends BaseService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Book getById(Long id) throws Exception {
        return super.findbyId(id, bookRepository);
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    public List<Book> getList(BookDTO dto) throws Exception {
        try {
            Specification<Book> specification = getSpecification(dto);
            List<Book> list = bookRepository.findAll(specification);
            return list;
        } catch (Exception e) {
            sysErrLog.error("查询列表失败:{}", e.getMessage());
            throw e;
        }
    }

    /**
     * 分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    public PageDTO<Book> getPage(BookDTO dto) throws Exception {
        try {
            Pageable pageable = getPageable(dto);
            Specification<Book> specification = getSpecification(dto);
            Page<Book> page = bookRepository.findAll(specification, pageable);
            PageDTO<Book> pageDTO = setPageInfoDTO((PageImpl<Book>) page);
            return pageDTO;
        } catch (Exception e) {
            sysErrLog.error("查询列表失败:{}", e.getMessage());
            throw e;
        }
    }

    /**
     * 设置分页内容
     *
     * @param page:
     * @return org.zero.base.dto.PageDTO<org.zero.bms.entity.Book>:
     * @author : cgl
     * @version : 1.0
     * @since 2020/8/20 18:07
     **/
    private PageDTO<Book> setPageInfoDTO(PageImpl<Book> page) {
        return new PageDTO<>(page);
    }

    /**
     * 构建查询条件
     *
     * @param dto:
     * @return org.springframework.data.jpa.domain.Specification<org.zero.bms.entity.Book>:
     * @author : cgl
     * @version : 1.0
     * @since 2020/8/20 18:04
     **/
    private Specification<Book> getSpecification(BookDTO dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // TODO 查询条件待补充
            if (!StringUtils.isEmpty(dto.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + dto.getName() + "%"));
            }
            if (dto.getCategoryId() != null) {
                try {
                    Category category = super.findbyId(dto.getCategoryId(), categoryRepository);
                    list.add(criteriaBuilder.like(root.get("categoryPath").as(String.class), category.getSelfPath() + "%"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        };
    }

    /**
     * 新增
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(BookInsertDTO dto) throws Exception {
        try {
            Category category = super.findbyId(dto.getCategoryId(), categoryRepository);
            dto.setCategoryPath(category.getSelfPath());
            super.update(dto, Book.class, bookRepository);
        } catch (Exception e) {
            sysErrLog.error("新增失败:{}，入参:{}", e.getMessage(), dto);
            throw e;
        }
    }

    /**
     * 修改
     *
     * @param dto 数据传输对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(BookUpdateDTO dto) throws Exception {
        try {
            if (dto.getCategoryId() != null) {
                Category category = super.findbyId(dto.getCategoryId(), categoryRepository);
                dto.setCategoryPath(category.getSelfPath());
            }
            super.update(dto, Book.class, bookRepository);
        } catch (Exception e) {
            sysErrLog.error("修改失败:{}，入参:{}", e.getMessage(), dto);
            throw e;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param dto 数据传输对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(BookUpdateDTO dto) throws Exception {
        try {
            super.deleteById(dto.getId(), bookRepository);
        } catch (Exception e) {
            sysErrLog.error("通过主键删除失败:{}，入参:{}", e.getMessage(), "id=" + dto.getId());
            throw e;
        }
    }

}
