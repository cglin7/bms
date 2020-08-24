package org.zero.base.service;

import org.zero.base.dto.BaseDTO;
import org.zero.base.entity.BaseEntity;
import org.zero.base.entity.BaseUserEntity;
import org.zero.base.enums.ExceptionEnum;
import org.zero.base.exception.BusinessException;
import org.zero.base.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author : cgl
 * @description : Service层基类
 * @date : 2018/12/28 17:27
 */
public class BaseService {

    /**
     * 业务日志
     */
    public Logger log = LoggerFactory.getLogger("business");

    /**
     * 系统异常日志
     */
    public Logger sysErrLog = LoggerFactory.getLogger("exception");

    /**
     * 拷贝非空字段
     *
     * @param dto  : 数据传输实体
     * @param po   : 数据表实体
     * @param user : 当前用户
     * @author : cgl
     * @version : 1.0
     * @since 2019/2/13 12:02
     **/
    public <O, D> void copyToPOignoreNull(O dto, D po, BaseUserEntity user) {
        copyToPOignoreNull(dto, po, user, new String[]{});
    }

    /**
     * 拷贝非空字段
     *
     * @param dto              : 数据传输类
     * @param po               : 实体类
     * @param user             : 当前用户
     * @param ignoreProperties : 忽略字段
     * @author : cgl
     * @version : 1.0
     * @since 2019/2/13 12:02
     **/
    public <O, D> void copyToPOignoreNull(O dto, D po, BaseUserEntity user, String[] ignoreProperties) {
        BeanUtils.copyProperties(dto, po, getNullPropertyNames(dto, ignoreProperties));
        setCreateOrUpdateInfo(po, user);
    }

    private String[] getNullPropertyNames(Object source, String[] ignoreProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        if (ignoreProperties != null && ignoreProperties.length > 0) {
            for (String ignore : ignoreProperties) {
                emptyNames.add(ignore);
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 根据id查询
     *
     * @param id: 主键
     * @return : T
     * @author : cgl
     * @version : 1.0
     * @since 2019/8/9 16:50
     **/
    public <T> T findbyId(Long id, BaseRepository repository) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("缺少 id 参数");
        }
        Optional<T> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new BusinessException(ExceptionEnum.DATA_NOT_FOUND.getMsg() + ", " + (repository.entityType() == null ? "" : repository.entityType() + ".") + "id=" + id);
        }
        return optional.get();
    }

    /**
     * 根据多id查询
     *
     * @param ids: 主键
     * @return : T
     * @author : cgl
     * @version : 1.0
     * @since 2019/8/9 16:50
     **/
    public <T> List<T> findbyIds(List<Long> ids, BaseRepository repository) throws Exception {
        if (ids == null || ids.size() == 0) {
            throw new BusinessException("缺少 ids 参数");
        }
        List<T> list = repository.findByIdIn(ids);
        return list;
    }

    /**
     * 根据id逻辑删除
     *
     * @param id: 主键
     * @return : T
     * @author : cgl
     * @version : 1.0
     * @since 2019/8/9 16:50
     **/
    public <T extends BaseEntity> T deleteById(Long id, BaseRepository repository) throws Exception {
        Optional<T> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new BusinessException(ExceptionEnum.DATA_NOT_FOUND);
        }
        T po = optional.get();
        po.setDeleted(true);
        repository.save(po);
        return po;
    }

    /**
     * 根据多id逻辑删除
     *
     * @param ids: 主键
     * @author : cgl
     * @version : 1.0
     * @since 2019/8/9 16:50
     **/
    public <T extends BaseEntity> void deleteByIds(List<Long> ids, BaseRepository repository) throws Exception {
        List<T> list = repository.findByIdIn(ids);
        for (T po : list) {
            po.setDeleted(true);
        }
        repository.saveAll(list);
    }

    /**
     * 实体更新基类方法
     *
     * @param dto:       数据传输实体
     * @param destClass: 目标类Class
     * @return : D
     * @author : cgl
     * @version : 1.0
     * @since 2019/7/29 14:58
     **/
    public <O extends BaseDTO, D> D update(O dto, Class<D> destClass, BaseRepository repository) throws Exception {
        D po;
        if (dto.getDeleted() == null) {
            dto.setDeleted(false);
        }
        if (dto.getId() == null) {
            po = toPO(dto, destClass, dto.getUser());
        } else {
            Optional<D> optional = repository.findById(dto.getId());
            if (!optional.isPresent()) {
                throw new BusinessException(ExceptionEnum.DATA_NOT_FOUND);
            }
            po = optional.get();
            copyToPOignoreNull(dto, po, dto.getUser());
        }
        repository.save(po);
        return po;
    }

    /**
     * 传输对象DTO复制属性到实体对象PO(同类型且同名的属性才复制)
     *
     * @param originDTO : 数据传输类
     * @param destClass : 目标类class对象
     * @return :  目标类对象
     * @author : cgl
     * @version : 1.0
     * @since 2019/2/13 12:04
     **/
    public <O, D> D toPO(O originDTO, Class<D> destClass, BaseUserEntity user) throws Exception {
        D itemPO = destClass.newInstance();
        BeanCopier beanCopier = BeanCopier.create(originDTO.getClass(), destClass, false);
        beanCopier.copy(originDTO, itemPO, null);
        setCreateOrUpdateInfo(itemPO, user);
        return itemPO;
    }

    /**
     * 设置新增人、新增时间、更新人、更新时间
     *
     * @param itemPO: 数据表实体
     * @param user:   当前用户
     * @return : void
     * @author : cgl
     * @version : 1.0
     * @since 2019/7/29 15:00
     **/
    public <T> void setCreateOrUpdateInfo(T itemPO, BaseUserEntity user) {
        // 新增时间为空，默认插入当前时间
        try {
            if (StringUtils.isEmpty(itemPO.getClass().getMethod("getCreatedTime").invoke(itemPO))) {
                itemPO.getClass().getMethod("setCreatedTime", Timestamp.class).invoke(itemPO, new Timestamp(System.currentTimeMillis()));
            }
        } catch (Exception e) {
        }
        // 更新时间为空，默认插入当前时间
        try {
            itemPO.getClass().getMethod("setUpdatedTime", Timestamp.class).invoke(itemPO, new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
        }

        if (user != null) {
            try {
                // 新增人id为空，默认插入当前登录人id
                if (StringUtils.isEmpty(itemPO.getClass().getMethod("getCreatorId").invoke(itemPO))) {
                    itemPO.getClass().getMethod("setCreatorId", Long.class).invoke(itemPO, user.getId());
                }
            } catch (Exception e) {
            }
            try {
                // 更新人id为空，默认插入当前登录人id
                itemPO.getClass().getMethod("setUpdaterId", Long.class).invoke(itemPO, user.getId());
            } catch (Exception e) {
            }
        }
    }

    /**
     * 传输对象DTO复制属性到实体对象PO(同类型且同名的属性才复制)
     *
     * @param originPO  : 实体类
     * @param destClass : 目标类class对象
     * @return :  目标类对象
     * @author : cgl
     * @version : 1.0
     * @since 2019/2/13 12:04
     **/
    public <O, D> D toDTO(O originPO, Class<D> destClass) throws Exception {
        D itemDTO = destClass.newInstance();
        BeanCopier beanCopier = BeanCopier.create(originPO.getClass(), destClass, false);
        beanCopier.copy(originPO, itemDTO, null);
        return itemDTO;
    }

    /**
     * 获取默认分页设置（按id升序）
     *
     * @param :
     * @return :
     * @author : cgl
     * @version : 1.0
     * @since 2019/3/11 10:58
     **/
    public Pageable getPageSetting(BaseDTO dto) {
        if (!dto.isNeedPage()) {
            return null;
        }
        return getPageable(dto);
    }

    public Pageable getPageable(BaseDTO dto) {
        Pageable pageable;
        if (dto.getCurrentPage() == null || dto.getCurrentPage() < 1) {
            dto.setCurrentPage(1);
        }
        if (dto.getLimit() == null || dto.getLimit() < 1) {
            dto.setLimit(10);
        }
        if (dto.getCurrentPage() >= 1) {
            dto.setCurrentPage(dto.getCurrentPage() - 1);
        }

        if (dto.getSortColumn() == null) {
            pageable = PageRequest.of(dto.getCurrentPage(), dto.getLimit());
            return pageable;
        }

        Sort.Direction sd;
        if ("asc".equalsIgnoreCase(dto.getSortDirection())) {
            sd = Sort.Direction.ASC;
        } else {
            sd = Sort.Direction.DESC;
        }
        pageable = PageRequest.of(dto.getCurrentPage(), dto.getLimit(), sd, dto.getSortColumn());
        return pageable;
    }

    public Pageable getPageSetting(BaseDTO dto, Sort sort) {
        Pageable pageable;

        if (dto.getCurrentPage() == null || dto.getCurrentPage() < 1) {
            dto.setCurrentPage(1);
        }
        if (dto.getLimit() == null || dto.getLimit() < 1) {
            dto.setLimit(10);
        }
        if (dto.getCurrentPage() >= 1) {
            dto.setCurrentPage(dto.getCurrentPage() - 1);
        }
        if (sort == null) {
            pageable = PageRequest.of(dto.getCurrentPage(), dto.getLimit());
        } else {
            pageable = PageRequest.of(dto.getCurrentPage(), dto.getLimit(), sort);
        }
        return pageable;
    }

    public <T extends BaseEntity> List<Long> getLongIds(List<T> list) {
        List<Long> ids = new ArrayList<>(list.size());
        for (T t : list) {
            ids.add(t.getId());
        }
        return ids;
    }

    public Date[] getIntegral(BaseDTO dto) {
        Date startTime = null;
        Date endTime = null;
        if (dto.getStart() != null) {
            startTime = new Date(dto.getStart());
        }
        if (dto.getEnd() != null) {
            endTime = new Date(dto.getEnd());
        }
        return new Date[]{startTime, endTime};
    }

    public <E extends Exception> void throwException(Exception e) throws E {
        throw (E) e;
    }

}
