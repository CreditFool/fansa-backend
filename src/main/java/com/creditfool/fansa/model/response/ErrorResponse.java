package com.creditfool.fansa.model.response;

import java.util.List;

public record ErrorResponse(
        String message,
        List<String> errors

) {
}
