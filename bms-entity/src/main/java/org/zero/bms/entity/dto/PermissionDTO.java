package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.zero.base.dto.BaseDTO;

import javax.persistence.Column;
import java.io.Serializable;

/**
  * 权限（Permission）数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:00
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDTO extends BaseDTO implements Serializable {
    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("接口url")
    private String url;

    @ApiModelProperty("描述")
    private String description;
}
