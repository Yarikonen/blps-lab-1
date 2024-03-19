package com.itmo.simaland.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itmo.simaland.model.enums.AddressType;
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

        @NotNull(message = "Customer id must not be null")
        @JsonProperty("customer")
        private Long customer;


        @JsonProperty("your_address")
        private String address;

        @NotNull(message = "Address type must not be null")
        @JsonProperty("address_type")
        private AddressType addressType;

        @JsonProperty("pick_up_point_id")
        private Long pickUpPointAddress;

        @NotEmpty(message = "Items must not be empty")
        @JsonProperty("items")
        private List<ItemQuantity> items;
}
