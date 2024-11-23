package com.creditfool.fansa.model.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String emailOrUsername,
        @NotBlank String password

) {
}
