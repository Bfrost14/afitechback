package sn.bfrost.myafiback.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.bfrost.myafiback.models.Token;
import sn.bfrost.myafiback.models.TokenType;
import sn.bfrost.myafiback.models.User;
import sn.bfrost.myafiback.repository.TokenRepository;
import sn.bfrost.myafiback.repository.UserRepository;
import sn.bfrost.myafiback.service.AuthenticationService;
import sn.bfrost.myafiback.service.JwtService;
import sn.bfrost.myafiback.service.dto.AuthenticationRequest;
import sn.bfrost.myafiback.service.dto.AuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if(user.getFirstConnection()){
            return  new AuthenticationResponse();
        }else{
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAccessToken(jwtToken);
            authenticationResponse.setRefreshToken(refreshToken);

            return authenticationResponse;
        }

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public AuthenticationResponse refreshToken(
            AuthenticationResponse refresh
    ) {
        final String refreshToken ;
        final String userEmail;
        System.out.println(request.getHeaderNames());
        if (refresh == null ) {
            return null;
        }

        refreshToken = refresh.getRefreshToken();

        userEmail = jwtService.extractUsername(refreshToken);
        System.out.println(userEmail);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                refresh.setAccessToken(accessToken);
                refresh.setRefreshToken(refreshToken);
            }
        }
        return refresh;
    }

    @Override
    public AuthenticationResponse resetPassword(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstConnection(false);
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtToken);
        authenticationResponse.setRefreshToken(refreshToken);

        return authenticationResponse;
    }
}
