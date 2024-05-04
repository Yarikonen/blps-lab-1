package com.itmo.simaland.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponse {

    @JsonProperty("id")
    private Long id;
}
