package com.lms.example.global.exception;

public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
