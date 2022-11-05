package com.bahar.gamescore.exception;

public class NotAuthorizedException extends ApplicationException{
    public NotAuthorizedException(int code, String message) {
        super(code, message);
    }
}
