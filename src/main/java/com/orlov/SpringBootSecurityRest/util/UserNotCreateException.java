package com.orlov.SpringBootSecurityRest.util;

public class UserNotCreateException extends RuntimeException {
    public UserNotCreateException(String message) {
        super(message);
    }
}
