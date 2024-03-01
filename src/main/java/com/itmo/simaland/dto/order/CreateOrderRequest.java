package com.itmo.simaland.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
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

        @JsonProperty("customer")
        private Long customer;

        @JsonProperty("pick_up_address")
        private String pickUpAddress;

        @JsonProperty("items")
        private List<Long> items;

}
