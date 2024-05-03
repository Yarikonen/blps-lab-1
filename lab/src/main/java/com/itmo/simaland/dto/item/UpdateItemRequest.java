package com.itmo.simaland.dto.item;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UpdateItemRequest {
    private String name;

    @Positive(message = "Цена должна быть положительным числом")
    private Integer price;
}
