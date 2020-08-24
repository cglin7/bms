package org.zero.bms.entity.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.zero.base.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
  * 角色(Role)实体类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/21 9:13
  **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "role", indexes = {@Index(name = "uniq_name", columnList = "name", unique = true)})
@Where(clause = "deleted=0")
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;    

    @Column(name = "name")
    private String name;

    /** 启用状态（0：禁用  1：启用） **/
    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permissionId", referencedColumnName = "id"))
    private Set<Permission> permissions;

}
