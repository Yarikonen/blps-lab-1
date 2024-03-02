package com.itmo.simaland.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

        @NotNull
        @JsonProperty("customer")
        private Long customer;

        @NotNull
        @NotEmpty
        @JsonProperty("pick_up_address")
        private String pickUpAddress;

        @NotEmpty
        @JsonProperty("items")
        private List<Long> items;

}
