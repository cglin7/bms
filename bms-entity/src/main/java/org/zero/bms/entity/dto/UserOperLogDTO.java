package org.zero.bms.entity.dto;

import lombok.Getter;
import lombok.Setter;
import org.zero.base.dto.BaseDTO;

import java.io.Serializable;

@Getter
@Setter
public class UserOperLogDTO extends BaseDTO implements Serializable {

    private Long userId;

    private String username; //用户名

    private String type;//操作类型

    private String operation; //操作

    private String method; //方法名

    private String params; //参数

    private String ip; //ip地址

}
