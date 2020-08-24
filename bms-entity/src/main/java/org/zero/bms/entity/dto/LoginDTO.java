package org.zero.bms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
  * @description : 用户名密码数据传输DTO类
  * @author : cgl
  * @date : 2018/12/28 16:32
 */
@ApiModel
@Getter
@Setter
public class LoginDTO {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
