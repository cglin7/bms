package org.zero.bms.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.zero.base.dto.PageDTO;
import org.zero.base.enums.ExceptionEnum;
import org.zero.base.exception.BusinessException;
import org.zero.base.service.BaseService;
import org.zero.bms.dao.PermissionRepository;
import org.zero.bms.dao.RoleRepository;
import org.zero.bms.dao.UserRepository;
import org.zero.bms.entity.dto.*;
import org.zero.bms.entity.po.Permission;
import org.zero.bms.entity.po.Role;
import org.zero.bms.entity.po.User;
import org.zero.bms.service.enums.UserStatusEnum;
import org.zero.util.CryptoUtil;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色（Role）服务层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Service
public class RoleService extends BaseService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Role getById(Long id) throws Exception {
        return super.findbyId(id, roleRepository);
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    public List<Role> getList(RoleDTO dto) throws Exception {
        try {
            Specification<Role> specification = getSpecification(dto);
            List<Role> list = roleRepository.findAll(specification);
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
    public PageDTO<Role> getPage(RoleDTO dto) throws Exception {
        try {
            Pageable pageable = getPageable(dto);
            Specification<Role> specification = getSpecification(dto);
            Page<Role> page = roleRepository.findAll(specification, pageable);
            PageDTO<Role> pageDTO = setPageInfoDTO((PageImpl<Role>) page);
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
    private PageDTO<Role> setPageInfoDTO(PageImpl<Role> page) {
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
    private Specification<Role> getSpecification(RoleDTO dto) {
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
    public void add(RoleInsertDTO dto) throws Exception {
        try {
            dto.setEnable(true);
            super.update(dto, Role.class, roleRepository);
        } catch (DataIntegrityViolationException e) {
            // 如果是索引冲突，提示冲突的列名
            if (e.getCause() instanceof ConstraintViolationException) {
                String constraintName = ((ConstraintViolationException)e.getCause()).getConstraintName();
                if (constraintName.contains("uniq_name".toUpperCase())) {
                    throw new BusinessException(ExceptionEnum.CONSTRAINT_VIOLATION.getCode(), "角色名称已被使用");
                }
            }
            throw e;
        }  catch (Exception e) {
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
    public void update(RoleUpdateDTO dto) throws Exception {
        try {
            super.update(dto, Role.class, roleRepository);
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
    public void deleteById(RoleUpdateDTO dto) throws Exception {
        try {
            super.deleteById(dto.getId(), roleRepository);
        } catch (Exception e) {
            sysErrLog.error("通过主键删除失败:{}，入参:{}", e.getMessage(), "id=" + dto.getId());
            throw e;
        }
    }

    /**
     * 设置角色权限
     *
     * @param dto 数据传输对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void setRolePermissions(UpdateRolePermissionsDTO dto) throws Exception {
        try {
            Role role = super.findbyId(dto.getRoleId(), roleRepository);
            if (dto.getPermissionIds() != null && dto.getPermissionIds().length > 0) {
                Set<Permission> permissions = new HashSet<>();
                for (Long pId : dto.getPermissionIds()) {
                    Permission permission = super.findbyId(pId, permissionRepository);
                    if (permission != null) {
                        permissions.add(permission);
                    }
                }
                role.setPermissions(permissions);
            }
            roleRepository.save(role);
        } catch (Exception e) {
            sysErrLog.error("设置角色权限失败：{}，入参：{}", e.getMessage(), dto);
            throw e;
        }
    }

}
