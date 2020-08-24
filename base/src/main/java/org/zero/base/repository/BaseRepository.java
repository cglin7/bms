package org.zero.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 持久层接口基类
 * @author : cgl
 * @version : 1.0
 * @since : 2019年8月9日
 **/
public interface BaseRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    /**
     * 返回持有实体的类型名
     *
     * @return java.lang.String:
     * @author : cgl
     * @version : 1.0
     * @since 2020/3/2 9:39
     **/
    default String entityType() {
        return null;
    }

    /**
     * 根据多id查询
     *
     * @param ids:
     * @return java.util.List<T>:
     * @author : cgl
     * @version : 1.0
     * @since 2019/9/9 9:09
     **/
    List<T> findByIdIn(Iterable<Long> ids);

}