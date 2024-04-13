package com.itmo.simaland.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itmo.simaland.model.enums.RoleEnum;
import com.itmo.simaland.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("role")
    private RoleEnum roleEnum;

    @JsonProperty("status")
    private Status status;
}
