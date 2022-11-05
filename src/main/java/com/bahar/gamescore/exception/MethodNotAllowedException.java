package com.bahar.gamescore.exception;


public class MethodNotAllowedException extends ApplicationException{
    MethodNotAllowedException(int code, String message) {
        super(code, message);
    }
}
