package com.itmo.simaland.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
