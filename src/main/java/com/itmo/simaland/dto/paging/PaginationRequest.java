package com.itmo.simaland.dto.paging;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
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

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;

    @Min(0)
    @JsonProperty("pageNumber")
    private Integer pageNumber;


    @Positive
    @Max(10)
    @JsonProperty("pageSize")
    private Integer pageSize;

    public PageRequest toPageRequest() {
        return PageRequest.of(
                Optional.ofNullable(pageNumber).orElse(DEFAULT_PAGE),
                Optional.ofNullable(pageSize).orElse(DEFAULT_SIZE)
        );
    }
}
