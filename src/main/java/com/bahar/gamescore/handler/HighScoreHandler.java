package com.bahar.gamescore.handler;

import com.bahar.gamescore.api.*;
import com.bahar.gamescore.exception.ApplicationExceptions;
import com.bahar.gamescore.exception.GlobalExceptionHandler;
import com.bahar.gamescore.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.king.api.*;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.util.LinkedHashMap;

public class HighScoreHandler extends Handler{
    private final ScoreService scoreService;
    private final UrlScoreComponent urlComponents;

    public HighScoreHandler(ScoreService scoreService, ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler,
                            UrlScoreComponent urlComponents) {
        super(objectMapper, exceptionHandler);
        this.scoreService = scoreService;
        this.urlComponents = urlComponents;
    }


    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte[] response;
        if ("GET".equals(exchange.getRequestMethod())) {
            ResponseEntity<HighScoreResponse> e = doGet();
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeCSVResponse(e.getBody());
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private ResponseEntity<HighScoreResponse> doGet() {

        LinkedHashMap<String, Integer> scores = scoreService.getHighScores(urlComponents.getLevelIdentifier());

        return new ResponseEntity<>(new HighScoreResponse(scores),
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}
