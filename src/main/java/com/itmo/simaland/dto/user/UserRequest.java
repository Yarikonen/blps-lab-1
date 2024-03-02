package com.itmo.simaland.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;



}
