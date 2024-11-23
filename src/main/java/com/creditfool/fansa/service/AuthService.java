package com.creditfool.fansa.service;

import com.creditfool.fansa.model.request.LoginRequest;
import com.creditfool.fansa.model.request.RegisterRequest;
import com.creditfool.fansa.model.response.LoginResponse;

public interface AuthService {
    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
