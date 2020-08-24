package org.zero.bms.dao;

import org.springframework.stereotype.Repository;
import org.zero.base.repository.BaseRepository;
import org.zero.bms.entity.po.User;

/**
  * 用户（User）持久层接口
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 17:54
  **/
@Repository
public interface UserRepository extends BaseRepository<User> {

    /**
     * 根据用户名查找
     *
     * @param name: 用户名
     * @return org.zero.bms.entity.User:
     * @author : cgl
     * @version : 1.0
     * @since 2020/8/21 10:38
     **/
    User findByName(String name);
}
