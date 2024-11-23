package com.creditfool.fansa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "fansa.admin")
public class InitAdminProperties {
    private String email;
    private String username;
    private String password;
}
