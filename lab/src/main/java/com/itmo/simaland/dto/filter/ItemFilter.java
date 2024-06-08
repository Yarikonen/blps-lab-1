package com.itmo.simaland.dto.filter;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemFilter {
    private String name;
    @Min(0)
    private Integer minPrice;

    private Integer maxPrice;
}
