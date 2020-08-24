package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
  * 分类（Category）数据传输类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:02
  **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryInsertDTO extends CategoryDTO {
    @JsonIgnore
    private Long id;

}