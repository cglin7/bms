package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.zero.base.dto.BaseDTO;

import java.io.Serializable;

/**
  * 分类（Category）数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:00
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO extends BaseDTO implements Serializable {
    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父分类id")
    private Long parentId;

    private String parentPath;

    private Integer level;

}
