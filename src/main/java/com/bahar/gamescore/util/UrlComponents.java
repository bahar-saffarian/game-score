package com.bahar.gamescore.util;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UrlComponents {
    String identifier;
    ServiceName serviceName;
}
