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
import org.zero.bms.dao.CategoryRepository;
import org.zero.bms.entity.dto.CategoryDTO;
import org.zero.bms.entity.dto.CategoryInsertDTO;
import org.zero.bms.entity.dto.CategoryUpdateDTO;
import org.zero.bms.entity.po.Category;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 分类（Category）服务层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Service
public class CategoryService extends BaseService {

    public static final String FOUR_DIGITS = "%04d";

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Category getById(Long id) throws Exception {
        return super.findbyId(id, categoryRepository);
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    public List<Category> getList(CategoryDTO dto) throws Exception {
        try {
            Specification<Category> specification = getSpecification(dto);
            List<Category> list = categoryRepository.findAll(specification);
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
    public PageDTO<Category> getPage(CategoryDTO dto) throws Exception {
        try {
            Pageable pageable = getPageable(dto);
            Specification<Category> specification = getSpecification(dto);
            Page<Category> page = categoryRepository.findAll(specification, pageable);
            PageDTO<Category> pageDTO = setPageInfoDTO((PageImpl<Category>) page);
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
    private PageDTO<Category> setPageInfoDTO(PageImpl<Category> page) {
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
    private Specification<Category> getSpecification(CategoryDTO dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // TODO 查询条件待补充
            if (!StringUtils.isEmpty(dto.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + dto.getName() + "%"));
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
    public void add(CategoryInsertDTO dto) throws Exception {
        try {
            Optional<Category> optionalParentCategory = categoryRepository.findById(dto.getParentId());
            if (optionalParentCategory.isPresent()) {
                Category parent = optionalParentCategory.get();
                dto.setLevel(parent.getLevel() + 1);
                dto.setParentPath(parent.getParentPath() + "-" + String.format(FOUR_DIGITS, parent.getId()));
            } else {
                dto.setLevel(0);
                dto.setParentPath(String.format(FOUR_DIGITS, 0));
            }
            super.update(dto, Category.class, categoryRepository);
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
    public void update(CategoryUpdateDTO dto) throws Exception {
        try {
            dto.setLevel(null);
            dto.setParentPath(null);

            Category self = super.findbyId(dto.getId(), categoryRepository);

            // 需要修改父结点的情况
            if (dto.getParentId() != null) {
                // 层级差
                int levelDiff;
                // 当前结点父路径
                String parentPath;

                Optional<Category> optionalParentCategory  = categoryRepository.findById(dto.getParentId());
                if (optionalParentCategory.isPresent()) {
                    Category parent = optionalParentCategory.get();
                    // 父结点存在的情况, 结点层级变为 parent.getLevel() + 1, 须和当前层级比较, 如有变化，则结点的所有子结点的层级要相应改变
                    levelDiff = self.getLevel() - (parent.getLevel() + 1);
                    parentPath = parent.getParentPath() + "-" + String.format(FOUR_DIGITS, parent.getId());
                } else {
                    // 父结点不存在的情况
                    levelDiff = self.getLevel();
                    parentPath = String.format(FOUR_DIGITS, 0);
                }

                // 修改所有子结点
                List<Category> children = categoryRepository.findByParentPathLike(self.getSelfPath() + "%");
                for (Category category : children) {
                    category.setLevel(category.getLevel() - levelDiff);
                    category.setParentPath(parentPath + "-" + String.format(FOUR_DIGITS, self.getId()));
                }
                categoryRepository.saveAll(children);

                // 修改自身
                dto.setLevel(self.getLevel() - levelDiff);
                dto.setParentPath(parentPath);
            }
            super.update(dto, Category.class, categoryRepository);
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
    public void deleteById(CategoryUpdateDTO dto) throws Exception {
        try {
            super.deleteById(dto.getId(), categoryRepository);
        } catch (Exception e) {
            sysErrLog.error("通过主键删除失败:{}，入参:{}", e.getMessage(), "id=" + dto.getId());
            throw e;
        }
    }

}
