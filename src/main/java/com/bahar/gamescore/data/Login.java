package com.bahar.gamescore.data;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class Login {
    private String userId;
    private String sessionKey;
    private Date lastSessionKeyUpdate;
}
