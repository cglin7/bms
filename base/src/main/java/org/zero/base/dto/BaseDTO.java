package org.zero.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.zero.base.entity.BaseUserEntity;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO {
    private Long id;
    @JsonIgnore
    private Boolean deleted;
    @JsonIgnore
    private BaseUserEntity user;
    private Timestamp updatedTime;
    private Timestamp createdTime;

    /************* 分页用参数 *************/
    Integer currentPage;
    Integer limit;
    String sortColumn;
    String sortDirection;
    boolean needPage = true; // 默认使用分页

    /************ 传入时间区间 ************/
    Long start;
    Long end;

}
