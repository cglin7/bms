package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.zero.base.dto.BaseDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
  * 更新用户角色关系数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:00
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRolesDTO implements Serializable {

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty("角色id数组")
    @NotEmpty(message = "角色id数组不能为空")
    private Long[] roleIds;
}
