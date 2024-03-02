package com.itmo.simaland.controller;


import com.itmo.simaland.dto.mapper.UserMapper;
import com.itmo.simaland.dto.user.UserRequest;
import com.itmo.simaland.dto.user.UserResponse;
import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.enums.Role;
import com.itmo.simaland.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.parameters.PathParameter;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User controller", description = "User controller")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @Operation(description = "Get user by id")
    @GetMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User found", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userMapper.toUserResponse( userService.getUserById(id)));
    }

    @Operation(description = "Create user")
    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserRequest userCreateRequest) {
        User user = userMapper.toUser(userCreateRequest);
        return userMapper.toUserResponse(
                userService.createUser(user)
        );
    }

    @Operation(description = "Update user role")
    @PatchMapping("/{id}/role")
    @ApiResponse(responseCode = "200", description = "Role updated")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable("id") Long id, @RequestParam("role") Role role) {
        User user = userService.updateUserRole(id, role);
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }
}
