package com.bahar.gamescore.util;

import com.bahar.gamescore.data.InmemoryLoginRepository;
import com.bahar.gamescore.data.Login;
import com.bahar.gamescore.exception.ApplicationExceptions;
import com.sun.net.httpserver.HttpExchange;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public class ApiUtils {
    public static Optional<UrlComponents> splitUrl(String query) {
        if (query == null || "".equals(query)) {
            return Optional.empty();
        }
        List<String> splitUrl = Pattern.compile("/").splitAsStream(query).collect(toList());
        if (splitUrl.size() != 3 || ServiceName.fromString(splitUrl.get(2)) == null) return Optional.empty();

        return Optional.of(new UrlComponents(splitUrl.get(1), ServiceName.fromString(splitUrl.get(2))));

    }

    public static Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));

    }

    private static String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is a required encoding", e);
        }
    }

    public static Login checkAuthority(HttpExchange exchange, InmemoryLoginRepository loginRepository) {
        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String sessionKey = params.getOrDefault("sessionkey", List.of("")).stream().findFirst().orElse("");
        Login login = loginRepository.getBySessionKey(sessionKey).orElseThrow(ApplicationExceptions.notAuthorized("session key is not valid"));
        if (login.getLastSessionKeyUpdate().before(new Date(System.currentTimeMillis() - 600 * 1000)))
            throw ApplicationExceptions.notAuthorized("session key is not valid").get();
        return login;
    }

    public static String convertWithIteration(LinkedHashMap<String, Integer> map) {
        StringBuilder mapAsString = new StringBuilder("");
        if (!map.isEmpty()) {
            map.keySet().stream().forEach(key ->
                    mapAsString.append(key + "=" + map.get(key) + ", ")
            );
            mapAsString.delete(mapAsString.length()-2, mapAsString.length());
        }
        return mapAsString.toString();
    }
}
