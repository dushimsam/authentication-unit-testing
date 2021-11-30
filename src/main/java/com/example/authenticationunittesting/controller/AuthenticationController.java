package com.example.authenticationunittesting.controller;

import com.example.authenticationunittesting.dto.authdto.AuthRequest;
import com.example.authenticationunittesting.dto.authdto.AuthResponse;
import com.example.authenticationunittesting.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/auth")
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        if (authenticationService.handleLogin(authRequest) == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(authenticationService.handleLogin(authRequest), HttpStatus.OK);
    }

}