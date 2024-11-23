package com.creditfool.fansa.util;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.creditfool.fansa.config.JwtProperties;
import com.creditfool.fansa.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            return JWT.create()
                    .withIssuer(jwtProperties.getIssuer())
                    .withSubject(user.getUid().toString())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(jwtProperties.getExpirationInSeconds()))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            log.error("generate token error: {}", e.getCause());
            throw e;
        }
    }
}
