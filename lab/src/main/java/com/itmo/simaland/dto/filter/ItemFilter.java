package com.itmo.simaland.dto.filter;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ItemFilter {
    private String name;

    @Min(0)
    private Integer minPrice;

    private Integer maxPrice;
}
