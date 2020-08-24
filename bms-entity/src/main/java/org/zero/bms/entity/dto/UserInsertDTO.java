package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
  * 用户（User）数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:02
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInsertDTO extends UserDTO {
    @JsonIgnore
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty("真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realname;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}