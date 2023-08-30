package com.logicea.cards.controller;

import com.logicea.cards.dto.LoginRequest;
import com.logicea.cards.dto.LoginResponse;
import com.logicea.cards.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String jwt = authenticationService.authenticate(loginRequest);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
