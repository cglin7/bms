package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
public class UserUpdateDTO extends UserDTO {
    @ApiModelProperty("用户id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("用户名")
    @Null(message = "已注册用户不能修改用户名")
    private String name;

}