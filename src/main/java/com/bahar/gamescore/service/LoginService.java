package com.bahar.gamescore.service;

import com.bahar.gamescore.data.InmemoryLoginRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginService {
    private final InmemoryLoginRepository loginRepository;
    public String createSessionKey(String userId) {

        return loginRepository.create(userId);
    }
}
