package com.bahar.gamescore.handler;

import com.bahar.gamescore.api.*;
import com.bahar.gamescore.exception.ApplicationExceptions;
import com.bahar.gamescore.service.ScoreService;
import com.bahar.gamescore.util.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.king.api.*;
import com.bahar.gamescore.data.InmemoryLoginRepository;
import com.bahar.gamescore.exception.GlobalExceptionHandler;
import com.bahar.gamescore.api.UrlScoreComponent;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;

public class ScoreHandler extends Handler{
    private final ScoreService scoreService;
    private final UrlScoreComponent urlComponents;
    private final InmemoryLoginRepository loginRepository;

    public ScoreHandler(ScoreService scoreService, ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler, UrlScoreComponent urlComponents, InmemoryLoginRepository loginRepository) {
        super(objectMapper, exceptionHandler);
        this.scoreService = scoreService;
        this.urlComponents = urlComponents;
        this.loginRepository = loginRepository;
    }


    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        this.urlComponents.setUserIdentifier(ApiUtils.checkAuthority(exchange, loginRepository).getUserId());
        if ("POST".equals(exchange.getRequestMethod())) {
            ResponseEntity<Response> e = doPost(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

    }

    private ResponseEntity<Response> doPost(InputStream is) {
        ScoreRequest scoreRequest = super.readRequest(is, ScoreRequest.class);

        scoreService.saveScore(urlComponents.getUserIdentifier(), urlComponents.getLevelIdentifier(), scoreRequest.getScore());


        return new ResponseEntity<Response>(new Response(),
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}
