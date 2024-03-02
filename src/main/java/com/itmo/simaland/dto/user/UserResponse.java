package com.itmo.simaland.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;
}
