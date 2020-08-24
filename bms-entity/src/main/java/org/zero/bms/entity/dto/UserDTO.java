package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.zero.base.dto.BaseDTO;

import java.io.Serializable;

/**
  * 用户（User）数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:00
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO implements Serializable {
    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("真实姓名")
    private String realname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户状态")
    private Integer status;

}
