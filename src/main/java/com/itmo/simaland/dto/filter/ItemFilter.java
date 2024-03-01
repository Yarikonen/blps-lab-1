package com.itmo.simaland.dto.filter;

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
    private Integer minPrice;
    private Integer maxPrice;
}
