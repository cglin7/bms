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
import org.zero.bms.dao.RoleRepository;
import org.zero.bms.dao.UserRepository;
import org.zero.bms.entity.dto.UpdateUserRolesDTO;
import org.zero.bms.entity.dto.UserDTO;
import org.zero.bms.entity.dto.UserInsertDTO;
import org.zero.bms.entity.dto.UserUpdateDTO;
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
 * 用户（User）服务层接口
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/7/13 12:27
 **/
@Service
public class UserService extends BaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public User getById(Long id) throws Exception {
        return super.findbyId(id, userRepository);
    }

    /**
     * 不分页查询数据
     *
     * @param dto 数据传输对象
     * @return 对象列表
     */
    public List<User> getList(UserDTO dto) throws Exception {
        try {
            Specification<User> specification = getSpecification(dto);
            List<User> list = userRepository.findAll(specification);
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
    public PageDTO<User> getPage(UserDTO dto) throws Exception {
        try {
            Pageable pageable = getPageable(dto);
            Specification<User> specification = getSpecification(dto);
            Page<User> page = userRepository.findAll(specification, pageable);
            PageDTO<User> pageDTO = setPageInfoDTO((PageImpl<User>) page);
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
    private PageDTO<User> setPageInfoDTO(PageImpl<User> page) {
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
    private Specification<User> getSpecification(UserDTO dto) {
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
    public void add(UserInsertDTO dto) throws Exception {
        try {
            String password = CryptoUtil.getMD5(dto.getPassword());
            dto.setPassword(password);
            dto.setStatus(UserStatusEnum.WAITING_AUDIT.getCode());
            super.update(dto, User.class, userRepository);
        } catch (DataIntegrityViolationException e) {
            // 如果是索引冲突，提示冲突的列名
            if (e.getCause() instanceof ConstraintViolationException) {
                String constraintName = ((ConstraintViolationException)e.getCause()).getConstraintName();
                if (constraintName.contains("uniq_name".toUpperCase())) {
                    throw new BusinessException(ExceptionEnum.CONSTRAINT_VIOLATION.getCode(), "用户名已被使用");
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
    public void update(UserUpdateDTO dto) throws Exception {
        try {
            super.update(dto, User.class, userRepository);
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
    public void deleteById(UserUpdateDTO dto) throws Exception {
        try {
            super.deleteById(dto.getId(), userRepository);
        } catch (Exception e) {
            sysErrLog.error("通过主键删除失败:{}，入参:{}", e.getMessage(), "id=" + dto.getId());
            throw e;
        }
    }

    /**
     * 设置用户角色
     *
     * @param dto 数据传输对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void setUserRoles(UpdateUserRolesDTO dto) throws Exception {
        try {
            User user = super.findbyId(dto.getUserId(), userRepository);
            if (dto.getRoleIds() != null && dto.getRoleIds().length > 0) {
                Set<Role> roles = new HashSet<>();
                for (Long pId : dto.getRoleIds()) {
                    Role role = super.findbyId(pId, roleRepository);
                    if (role != null) {
                        roles.add(role);
                    }
                }
                user.setRoles(roles);
            }
            userRepository.save(user);
        } catch (Exception e) {
            sysErrLog.error("设置用户角色失败：{}，入参：{}", e.getMessage(), dto);
            throw e;
        }
    }


}
