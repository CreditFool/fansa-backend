package com.creditfool.fansa.model.request;

import com.creditfool.fansa.constant.Message;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank @Email String email,

        @NotBlank @Pattern(regexp = "^\\w{3,20}$", message = Message.USERNAME_WRONG_PATTERN) String username,

        @NotBlank @Pattern(regexp = "^[A-Za-z0-9@$!%*?&_]{8,20}$", message = "Password must be between 8 and 20 characters and cannot contain spaces") String password

) {
}
