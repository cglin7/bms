package org.zero.bms.dao;

import org.springframework.stereotype.Repository;
import org.zero.base.repository.BaseRepository;
import org.zero.bms.entity.po.UserOperLog;

/**
  * 用户操作日志（UserOperLog）持久层接口
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 17:54
  **/
@Repository
public interface UserOperLogRepository extends BaseRepository<UserOperLog> {

}
