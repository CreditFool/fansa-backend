package com.creditfool.fansa.constant;

public class Message {
    private Message() {
    }

    public static final String LOGIN_SUCCESS = "Login successfully";
    public static final String REGISTER_SUCCESS = "User registered successfully";

    public static final String USERNAME_WRONG_PATTERN = "Username must be between 3 and 20 characters and only contain letters and numbers";
    public static final String PASSWORD_WRONG_PATTERN = "Password must be between 8 and 20 characters";

    public static final String USER_NOT_FOUND = "User not found";
    public static final String USERNAME_OR_EMAIL_ALREADY_EXIST = "Username or Email already exist";
    public static final String INVALID_CREDENTIAL = "Invalid Username/Email or Password";

}