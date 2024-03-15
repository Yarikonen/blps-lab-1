package com.itmo.simaland.dto.paging;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListResponse<T> {

    private List<T> content;

    private Integer totalElements;

    private Integer size;

    private Integer pageNumber;

    private Boolean last;



}
