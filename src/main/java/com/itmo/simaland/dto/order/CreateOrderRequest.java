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

        @NotNull
        @JsonProperty("customer")
        private Long customer;


        @JsonProperty("your_address")
        private String address;

        @NotNull
        @JsonProperty("address_type")
        private AddressType addressType;

        @JsonProperty("pick_up_point_id")
        private Long pickUpPointAddress;

        @NotEmpty
        @JsonProperty("items")
        private List<Long> items;

}
