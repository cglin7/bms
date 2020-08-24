package org.zero.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;

/**
  * 分页内容展示数据类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/8/20 18:06
  **/
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDTO<T> {
    private List<T> content;

    private Long totalElements;

    private Integer totalPages;

    private Boolean hasNext;

    private Boolean last;

    private Boolean first;

    private Integer numberOfElements;

    private Boolean empty;

    private Set<T> set;

    public PageDTO(PageImpl<T> page) {
        this.setContent(page.getContent());
        this.setFirst(page.isFirst());
        this.setLast(page.isLast());
        this.setTotalElements(page.getTotalElements());
        this.setNumberOfElements(page.getNumberOfElements());
        this.setTotalPages(page.getTotalPages());
        this.setHasNext(page.hasNext());
    }

}
