package com.bahar.gamescore;

import com.bahar.gamescore.data.InmemoryLoginRepository;
import com.bahar.gamescore.data.InmemoryScoreRepository;
import com.bahar.gamescore.exception.GlobalExceptionHandler;
import com.bahar.gamescore.service.LoginService;
import com.bahar.gamescore.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Configuration {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final InmemoryLoginRepository LOGIN_REPOSITORY = new InmemoryLoginRepository();
    private static final LoginService LOGIN_SERVICE = new LoginService(LOGIN_REPOSITORY);

    private static final InmemoryScoreRepository SCORE_REPOSITORY = new InmemoryScoreRepository();
    private static final ScoreService SCORE_SERVICE = new ScoreService(SCORE_REPOSITORY);
    private static final GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    static LoginService getLoginService() {
        return LOGIN_SERVICE;
    }

    static ScoreService getScoreService() {
        return SCORE_SERVICE;
    }

    static InmemoryLoginRepository getLoginRepository() {
        return LOGIN_REPOSITORY;
    }

    public static GlobalExceptionHandler getErrorHandler() {
        return GLOBAL_ERROR_HANDLER;
    }
}
