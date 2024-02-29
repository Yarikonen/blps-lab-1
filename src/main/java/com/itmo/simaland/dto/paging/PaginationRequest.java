package com.itmo.simaland.dto.paging;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

    private static final Integer defaultPage = 0;
    private static final Integer defaultSize = 10;

    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("page_size")
    private Integer pageSize;

    public PageRequest toPageRequest() {
        return PageRequest.of(
                Optional.ofNullable(pageNumber).orElse(defaultPage),
                Optional.ofNullable(pageSize).orElse(defaultSize)
        );
    }
}
