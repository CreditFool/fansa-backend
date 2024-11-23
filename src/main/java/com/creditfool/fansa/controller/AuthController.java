package com.creditfool.fansa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditfool.fansa.constant.APIPath;
import com.creditfool.fansa.constant.Message;
import com.creditfool.fansa.model.request.LoginRequest;
import com.creditfool.fansa.model.request.RegisterRequest;
import com.creditfool.fansa.model.response.CommonResponse;
import com.creditfool.fansa.model.response.LoginResponse;
import com.creditfool.fansa.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(APIPath.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(APIPath.LOGIN)
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody @Validated LoginRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommonResponse<>(Message.LOGIN_SUCCESS, authService.login(request)));
    }

    @PostMapping(APIPath.REGISTER)
    public ResponseEntity<CommonResponse<Void>> register(@RequestBody @Validated RegisterRequest request) {

        authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonResponse<>(Message.REGISTER_SUCCESS, null));
    }

}
