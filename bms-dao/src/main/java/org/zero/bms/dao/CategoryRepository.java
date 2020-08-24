package org.zero.bms.dao;

import org.springframework.stereotype.Repository;
import org.zero.base.repository.BaseRepository;
import org.zero.bms.entity.po.Category;

import java.util.List;

/**
  * 分类（Category）持久层接口
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 17:54
  **/
@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    /**
     * 按父路径进行模糊查询
     *
     * @param parentPath:
     * @return java.util.List<org.zero.bms.entity.Category>:
     * @author : cgl
     * @version : 1.0
     * @since 2020/8/21 16:53
     **/
    List<Category> findByParentPathLike(String parentPath);
}
