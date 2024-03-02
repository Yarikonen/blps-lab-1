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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User controller", description = "User controller")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/{id}")
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
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        if(userService.isUsernameExists(userRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.toUser(userRequest);
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(userMapper.toUserResponse(savedUser), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}/role")
    @Operation(description = "Update user role")
    @ApiResponse(responseCode = "200", description = "Role updated")
    public UserResponse updateUserRole(@PathVariable("id") Long id, @RequestParam("role") Role role) {
        User user = userService.updateUserRole(id, role);
        return (userMapper.toUserResponse(user));
    }

    @PatchMapping("/{id}/status")
    @Operation(description = "Update user status")
    @ApiResponse(responseCode = "200", description = "Status updated")
    public UserResponse updateUserStatus(@PathVariable("id") Long id, @RequestParam("status") Status status) {
        User user = userService.updateUserStatus(id, status);
        return (userMapper.toUserResponse(user));
    }

    @PatchMapping("/{id}/username")
    @Operation(summary = "Update user's username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Username updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid username provided", content = @Content)
    })
    public ResponseEntity<UserResponse> updateUsername(@PathVariable("id") Long id, @RequestParam("username") String username) {
        try {
            if(userService.isUsernameExists(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            User user = userService.updateUsername(id, username);
            UserResponse userResponse = userMapper.toUserResponse(user);
            return ResponseEntity.ok(userResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
