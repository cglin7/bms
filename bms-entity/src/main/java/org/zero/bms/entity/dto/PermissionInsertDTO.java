package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
  * 权限（Permission）数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:02
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionInsertDTO extends PermissionDTO {
    @JsonIgnore
    private Long id;

    @ApiModelProperty("权限名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("接口url")
    @NotBlank(message = "url不能为空")
    private String url;

}