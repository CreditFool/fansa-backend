package com.creditfool.fansa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "fansa.jwt")
public class JwtProperties {
    private String issuer;
    private String secret;
    private Long expirationInSeconds;
}
