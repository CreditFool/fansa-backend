package com.creditfool.fansa.model.response;

import java.util.List;

public record LoginResponse(
        String token,
        String uid,
        String username,
        List<String> roles

) {
}
