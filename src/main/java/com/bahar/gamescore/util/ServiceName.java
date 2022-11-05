package com.bahar.gamescore.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@AllArgsConstructor
@Getter
public enum ServiceName {
    LOGIN("login"),
    SCORE("score"),
    HIGH_SCORE_LIST("highscorelist");

    final private String value;

    private static Map<String, ServiceName> reverseLookup = Arrays.stream(values())
            .collect(Collectors.toMap(ServiceName::getValue, Function.identity()));

    public static ServiceName fromString(final String value) {
        return reverseLookup.getOrDefault(value, null);
    }
}
