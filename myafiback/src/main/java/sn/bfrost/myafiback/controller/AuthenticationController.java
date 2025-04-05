package sn.bfrost.myafiback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.bfrost.myafiback.service.AuthenticationService;
import sn.bfrost.myafiback.service.dto.AuthenticationRequest;
import sn.bfrost.myafiback.service.dto.AuthenticationResponse;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshToken(
            @RequestBody AuthenticationResponse refresh
    ) throws IOException {
       return service.refreshToken(refresh);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthenticationResponse> resetPassword(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.resetPassword(request));
    }

}