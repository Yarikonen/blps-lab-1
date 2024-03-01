package com.itmo.simaland.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateOrderRequest {

        @JsonProperty("customer_id")
        private Long customerId;

        @JsonProperty("order_date")
        private Long orderDate;

        @JsonProperty("pick_up_address")
        private String pickUpAddress;

        @JsonProperty("items")
        private List<Long> items;


}
