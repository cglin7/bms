package org.zero.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zero.base.service.BaseService;
import org.zero.bms.dao.UserOperLogRepository;
import org.zero.bms.entity.dto.UserOperLogDTO;
import org.zero.bms.entity.po.UserOperLog;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserOperLogService extends BaseService {

    @Autowired
    private UserOperLogRepository userOperLogRepository;

    @Transactional(rollbackFor = Exception.class)
    public void add(UserOperLogDTO dto) throws Exception {
        super.update(dto, UserOperLog.class, userOperLogRepository);
    }

    public Page<UserOperLog> getPage(Pageable pageable, UserOperLogDTO dto) throws Exception {
        Page<UserOperLog> page = userOperLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        return page;
    }

}
