package com.creditfool.fansa.model.response;

public record CommonResponse<T>(
        String message,
        T data

) {
}
