package com.itmo.simaland.dto.filter;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemFilter {
    private String name;

    @Min(0)
    private Integer minPrice;

    private Integer maxPrice;
}
