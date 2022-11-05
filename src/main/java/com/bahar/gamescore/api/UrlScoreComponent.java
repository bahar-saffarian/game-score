package com.bahar.gamescore.api;

import com.bahar.gamescore.util.ServiceName;
import lombok.Value;

@Value
public class UrlScoreComponent extends UrlLoginComponents {
    private final String levelIdentifier;

    public UrlScoreComponent(String userIdentifier, ServiceName serviceName, String levelIdentifier) {
        super(userIdentifier, serviceName);
        this.levelIdentifier = levelIdentifier;
    }
}
