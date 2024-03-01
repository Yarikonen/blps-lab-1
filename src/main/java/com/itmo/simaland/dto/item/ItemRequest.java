package com.itmo.simaland.dto.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ItemRequest {
    @NotNull(message = "Название не может быть пустым")
    private String name;

    @Positive(message = "Цена должна быть положительным числом")
    @NotNull(message = "Цена не может быть пустой")
    private Integer price;
}
