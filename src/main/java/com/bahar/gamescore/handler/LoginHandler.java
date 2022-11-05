package com.bahar.gamescore.handler;

import com.bahar.gamescore.api.UrlLoginComponents;
import com.bahar.gamescore.exception.ApplicationExceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bahar.gamescore.api.Constants;
import com.bahar.gamescore.api.LoginResponse;
import com.bahar.gamescore.api.ResponseEntity;
import com.bahar.gamescore.api.StatusCode;
import com.bahar.gamescore.exception.GlobalExceptionHandler;
import com.bahar.gamescore.service.LoginService;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;

public class LoginHandler extends Handler{
    private final LoginService loginService;
    private final UrlLoginComponents urlLoginComponents;
    public LoginHandler(LoginService loginService, ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler, UrlLoginComponents urlLoginComponents) {
        super(objectMapper, exceptionHandler);
        this.loginService = loginService;
        this.urlLoginComponents = urlLoginComponents;
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte[] response;
        if ("GET".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doGet();
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private ResponseEntity<LoginResponse> doGet() {

        String sessionKey = loginService.createSessionKey(urlLoginComponents.getUserIdentifier());

        LoginResponse response = new LoginResponse(sessionKey);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}
