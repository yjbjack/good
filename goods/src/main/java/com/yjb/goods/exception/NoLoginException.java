package com.yjb.goods.exception;

public class NoLoginException extends RuntimeException{
    public NoLoginException(String message) {
        super(message);
    }
}
