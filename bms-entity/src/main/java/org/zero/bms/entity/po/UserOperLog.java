package org.zero.bms.entity.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.zero.base.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户操作日志（UserOperLog）实体类
 * @author : cgl
 * @version : 1.0
 * @since : 2020/8/20 18:25
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user_oper_log")
@Where(clause = "deleted=0")
public class UserOperLog extends BaseEntity implements Serializable {

    @Column(name = "userId")
    private Long userId;

    @Column(name = "username")
    private String username; //用户名

    @Column(name = "type")
    private String type;//操作类型

    @Column(name = "operation")
    private String operation; //操作

    @Column(name = "method")
    private String method; //方法名

    @Column(name = "params")
    private String params; //参数

    @Column(name = "ip")
    private String ip; //ip地址

}
