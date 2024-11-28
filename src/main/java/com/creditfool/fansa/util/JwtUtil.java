package com.creditfool.fansa.util;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.creditfool.fansa.config.JwtProperties;
import com.creditfool.fansa.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtProperties.getSecret());
    }

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(jwtProperties.getIssuer())
                    .withSubject(user.getUid().toString())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(jwtProperties.getExpirationInSeconds()))
                    .sign(getAlgorithm());

        } catch (JWTCreationException e) {
            log.error("generate token error: {}", e.getCause());
            throw e;
        }
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(getAlgorithm())
                    .withIssuer(jwtProperties.getIssuer())
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            log.error("validate token error: {}", e.getCause());
            return false;
        }
    }

    public Map<String, String> getPayload(String token) {
        Map<String, String> payload = new HashMap<>();
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            payload.put("uid", decodedJWT.getSubject());
            return payload;

        } catch (Exception e) {
            log.error("decode token error: {}", e.getCause());
            return payload;
        }
    }
}
