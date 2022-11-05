package com.bahar.gamescore.data;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InmemoryLoginRepository {
    private static final Map<String, Login> LOGIN_STORE = new ConcurrentHashMap();

    public String create(String userId) {
        String sessionKey = UUID.randomUUID().toString();
        Login login = Login.builder()
                .userId(userId)
                .sessionKey(sessionKey)
                .lastSessionKeyUpdate(new Date())
                .build();
        LOGIN_STORE.put(login.getSessionKey(), login);

        return sessionKey;
    }

    public Optional<Login> getBySessionKey(String sessionKey) {
        return Optional.ofNullable(LOGIN_STORE.get(sessionKey));
    }
}
