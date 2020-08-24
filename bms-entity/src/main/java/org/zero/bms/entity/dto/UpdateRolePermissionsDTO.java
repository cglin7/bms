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
  * 更新角色权限关系数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:00
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateRolePermissionsDTO implements Serializable {

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty("权限id数组")
    @NotEmpty(message = "权限id数组不能为空")
    private Long[] permissionIds;
}
