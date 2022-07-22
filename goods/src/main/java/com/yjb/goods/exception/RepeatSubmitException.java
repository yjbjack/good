package com.yjb.goods.exception;

public class RepeatSubmitException extends RuntimeException{
    public RepeatSubmitException(String message) {
        super(message);
    }
}
