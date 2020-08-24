package org.zero.bms.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.zero.base.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户（User）实体类
 * @author : cgl
 * @version : 1.0
 * @since : 2020/8/20 18:25
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user", indexes = {@Index(name = "uniq_name", columnList = "name", unique = true)})
@Where(clause = "deleted=0")
public class User extends BaseEntity implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "realname")
    private String realname;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    Set<Role> roles;

    @Transient
    private Set<Permission> permissions;

}
