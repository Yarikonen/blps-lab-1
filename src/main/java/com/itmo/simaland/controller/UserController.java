package com.itmo.simaland.controller;


import com.itmo.simaland.dto.mapper.UserMapper;
import com.itmo.simaland.dto.user.UserRequest;
import com.itmo.simaland.dto.user.UserResponse;
import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.enums.Role;
import com.itmo.simaland.model.enums.Status;
import com.itmo.simaland.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public UserResponse getUserById(@PathVariable("id") Long id) {
        return userMapper.toUserResponse( userService.getUserById(id));
    }

    @Operation(description = "Create user")
    @PostMapping("/create")
    public UserResponse createUser(@RequestBody @Valid UserRequest userCreateRequest) {
        User user = userMapper.toUser(userCreateRequest);
        return userMapper.toUserResponse(
                userService.createUser(user)
        );
    }

    @Operation(description = "Update user role")
    @PatchMapping("/{id}/role")
    @ApiResponse(responseCode = "200", description = "Role updated")
    public UserResponse updateUserRole(@PathVariable("id") Long id, @RequestParam("role") Role role) {
        User user = userService.updateUserRole(id, role);
        return (userMapper.toUserResponse(user));
    }

    @Operation(description = "Update user status")
    @PatchMapping("/{id}/status")
    @ApiResponse(responseCode = "200", description = "Status updated")
    public UserResponse updateUserStatus(@PathVariable("id") Long id, @RequestParam("status") Status status) {
        User user = userService.updateUserStatus(id, status);
        return (userMapper.toUserResponse(user));
    }
}
