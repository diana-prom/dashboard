package com.dashboard.nutrition.exception;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage(){
        return message;
    }

    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }
}
