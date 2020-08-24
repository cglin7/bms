package org.zero.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 用户基类
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/13 11:21
 **/
@Setter
@Getter
@MappedSuperclass
public class BaseUserEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

}
