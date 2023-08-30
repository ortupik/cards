package com.logicea.cards.controller;

import com.logicea.cards.model.UserEntity;
import com.logicea.cards.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(value = "User Management", description = "Operations pertaining to users in the Cards application")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "View a list of available users", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 403, message = "Access to the resource is forbidden"),
            @ApiResponse(code = 404, message = "The resource not found")
    })
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.findAllUsers();
    }

    @ApiOperation(value = "Get a user by its email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable("email") String email) {
        UserEntity user = userService.findByEmail(email);
        if(user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully created"),
            @ApiResponse(code = 400, message = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @ApiOperation(value = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 400, message = "Invalid input data")
    })
    @PutMapping("/{email}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("email") String email, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(email, user);
        if(updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete a user by its email")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted user"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        boolean deleted = userService.deleteUser(email);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
