package org.zero.bms.entity.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.zero.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
  * 图书（Book）实体类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:25
  **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "book")
@Where(clause = "deleted=0")
public class Book extends BaseEntity implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "categoryPath")
    private String categoryPath;

}
