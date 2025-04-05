package sn.bfrost.myafiback.service;

import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import sn.bfrost.myafiback.models.User;

public interface JwtService {


    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User userDetails);

    String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    );

    String generateRefreshToken(
            User userDetails
    );

    boolean isTokenValid(String token, UserDetails userDetails);

}
