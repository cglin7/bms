package org.zero.bms.entity.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.zero.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 分类（Category）实体类
 * @author : cgl
 * @version : 1.0
 * @since : 2020/8/20 18:25
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "category")
@Where(clause = "deleted=0")
public class Category extends BaseEntity implements Serializable {
    public static final String FOUR_DIGITS = "%04d";

    @Column(name = "name")
    private String name;

    @Column(name = "parentId")
    private Long parentId;

    @Column(name = "parentPath")
    private String parentPath;

    @Column(name = "level")
    private Integer level;

    @Transient
    private String selfPath;

    public String getSelfPath() {
        return parentPath + "-" + String.format(FOUR_DIGITS, this.getId());
    }

}
