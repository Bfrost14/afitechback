package com.bfrost.universite.service.impl;

import com.bfrost.universite.config.JwtService;
import com.bfrost.universite.repository.UserRepository;
import com.bfrost.universite.service.dto.AuthenticationRequest;
import com.bfrost.universite.service.dto.AuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;



    public AuthenticationResponse authenticate(AuthenticationRequest request) throws MessagingException {
        Authentication authentification = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findOneByLogin(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateRefreshToken(authentification);


        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(!user.getFirstConnection()){
            authenticationResponse.setAccessToken(jwtToken);
            authenticationResponse.setMessage(null);
        }else{
           authenticationResponse.setMessage("Changez votre mot de passe");
        }


        return authenticationResponse;
    }




    public AuthenticationResponse resetPassword(AuthenticationRequest request) {
        var user = repository.findOneByLogin(request.getEmail())
                .orElseThrow();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstConnection(false);
        repository.save(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setMessage("Le mot de passe a été modifié avec success");


        return authenticationResponse;
    }


}
