package com.example.training.common.exceptions;

public class PermissionDenyException extends RuntimeException{
    public PermissionDenyException(String msg) {
        super(msg);
    }
}
