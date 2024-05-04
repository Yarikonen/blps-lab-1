package com.itmo.simaland.controller;


import com.itmo.simaland.dto.mapper.UserMapper;
import com.itmo.simaland.dto.user.UserRequest;
import com.itmo.simaland.dto.user.UserResponse;
import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.enums.RoleEnum;
import com.itmo.simaland.model.enums.Status;
import com.itmo.simaland.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "User controller", description = "User controller")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Get user by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User found", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    public UserResponse getUserById(@PathVariable("id") Long id) {
        return userMapper.toUserResponse( userService.getUserById(id));
    }

    @PostMapping
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided", content = @Content),
            @ApiResponse(responseCode = "409", description = "Username already exists", content = @Content)
    })
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        User savedUser = userService.createUser(user);
        return userMapper.toUserResponse(savedUser);
    }


    @PatchMapping("/{id}/role")
    @Operation(description = "Update user role")
    @ApiResponse(responseCode = "200", description = "Role updated", content = @Content)
    public UserResponse updateUserRole(@PathVariable("id") Long id, @RequestParam("roleEnum") RoleEnum roleEnum) {
        User user = userService.updateUserRole(id, roleEnum);
        return userMapper.toUserResponse(user);
    }

    @PatchMapping("/{id}/status")
    @Operation(description = "Update user status")
    @ApiResponse(responseCode = "200", description = "Status updated", content = @Content)
    public UserResponse updateUserStatus(@PathVariable("id") Long id, @RequestParam("status") Status status) {
        User user = userService.updateUserStatus(id, status);
        return userMapper.toUserResponse(user);
    }

    @PatchMapping("/{id}/username")
    @Operation(summary = "Update user's username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Username updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid username provided", content = @Content)
    })
    public UserResponse updateUsername(@PathVariable("id") Long id, @NotNull @NotEmpty @RequestParam("username") String username) {
        User user = userService.updateUsername(id, username);
        return userMapper.toUserResponse(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
