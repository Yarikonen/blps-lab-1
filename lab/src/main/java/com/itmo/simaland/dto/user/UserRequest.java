package com.itmo.simaland.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    @NotEmpty
    @JsonProperty("username")
    @NotNull
    private String username;

    @NotEmpty
    @JsonProperty("password")
    @NotNull
    private String password;
}
