package com.bfrost.universite.web.rest;

import com.bfrost.universite.service.UserService;
import com.bfrost.universite.service.dto.AuthenticationRequest;
import com.bfrost.universite.service.dto.AuthenticationResponse;
import com.bfrost.universite.service.dto.RegisterRequest;
import com.bfrost.universite.service.impl.AuthenticationService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws MessagingException {
        return ResponseEntity.ok(service.authenticate(request));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.resetPassword(request));
    }


}