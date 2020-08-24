package org.zero.bms.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 图书（Book）数据传输类
 * @author : cgl
 * @version : 1.0
 * @since : 2020/8/20 18:02
 **/
@ApiModel
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookUpdateDTO extends BookDTO {
    @ApiModelProperty("图书id")
    @NotNull(message = "id不能为空")
    private Long id;

}