package org.zero.bms.entity.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.zero.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

/**
  * 权限（Permission）实体类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/21 9:24
  **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "permission", indexes = {@Index(name = "uniq_name", columnList = "name", unique = true)})
@Where(clause = "deleted=0")
public class Permission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 名称 **/
    @Column(name = "name")
    private String name;
                    
    /** Url **/
    @Column(name = "url")
    private String url;

    /** 描述 **/
    @Column(name = "description")
    private String description;

}
