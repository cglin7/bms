package org.zero.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 实体类基类
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2019/2/13 11:21
 **/
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "opVersion")
    @Version
    private Integer opVersion;

    @JsonIgnore
    @Column(name = "createdTime")
    private Timestamp createdTime;

    @JsonIgnore
    @Column(name = "updatedTime")
    private Timestamp updatedTime;

    @JsonIgnore
    @Column(name = "deleted")
    private Boolean deleted = false;

    @Override
    public boolean equals(Object other) {
        return (other != null) && ((other instanceof BaseEntity)) && (((BaseEntity) other).getId().equals(this.id));
    }

}
