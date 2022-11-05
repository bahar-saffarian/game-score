package com.bahar.gamescore.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class ApiUtilsTest {

    @Test
    void loginSplitUrl() {
        //Given
        String url = "/<userid>/login";

        //When
        Optional<UrlComponents> urlComponents = ApiUtils.splitUrl(url);

        //Then
        Assertions.assertTrue(urlComponents.isPresent());
        Assertions.assertEquals(urlComponents.get().getIdentifier(), "<userid>");
        Assertions.assertEquals(urlComponents.get().getServiceName(), ServiceName.LOGIN);
    }

    @Test
    void scoreSplitUrl() {
        //Given
        String url = "/<userid>/score";

        //When
        Optional<UrlComponents> urlComponents = ApiUtils.splitUrl(url);

        //Then
        Assertions.assertTrue(urlComponents.isPresent());
        Assertions.assertEquals(urlComponents.get().getIdentifier(), "<userid>");
        Assertions.assertEquals(urlComponents.get().getServiceName(), ServiceName.SCORE);
    }

    @Test
    void highScoreListSplitUrl() {
        //Given
        String url = "/<userid>/highscorelist";

        //When
        Optional<UrlComponents> urlComponents = ApiUtils.splitUrl(url);

        //Then
        Assertions.assertTrue(urlComponents.isPresent());
        Assertions.assertEquals(urlComponents.get().getIdentifier(), "<userid>");
        Assertions.assertEquals(urlComponents.get().getServiceName(), ServiceName.HIGH_SCORE_LIST);
    }

    @Test
    void NotExistServiceSplitUrl() {
        //Given
        String url = "/<userid>/xyz";

        //When
        Optional<UrlComponents> urlComponents = ApiUtils.splitUrl(url);

        //Then
        Assertions.assertTrue(urlComponents.isEmpty());
    }

    @Test
    void NotValidSplitUrl() {
        //Given
        String url = "/";

        //When
        Optional<UrlComponents> urlComponents = ApiUtils.splitUrl(url);

        //Then
        Assertions.assertTrue(urlComponents.isEmpty());
    }

    @Test
    void splitQuery() {
        //Given
        String urlQuery = "sessionKey=123";

        //When
        Map<String, List<String>> params = ApiUtils.splitQuery(urlQuery);

        //Then
        Assertions.assertFalse(params.isEmpty());
        Assertions.assertNotNull(params.keySet().stream().findFirst().orElseGet(null));
        Assertions.assertEquals(params.keySet().stream().findFirst().get(), "sessionKey");
        Assertions.assertEquals(params.get("sessionKey").size(), 1);
        Assertions.assertEquals(params.get("sessionKey").get(0), "123");
    }

    @Test
    void splitWrongPatterQuery() {
        //Given
        String urlQuery = "sessionKey";

        //When
        Map<String, List<String>> params = ApiUtils.splitQuery(urlQuery);

        //Then
        Assertions.assertFalse(params.isEmpty());
        Assertions.assertNotNull(params.keySet().stream().findFirst().orElseGet(null));
        Assertions.assertEquals(params.keySet().stream().findFirst().get(), "sessionKey");
        Assertions.assertEquals(params.get("sessionKey").size(), 1);
        Assertions.assertNull(params.get("sessionKey").get(0));
    }

    @Test
    void splitEmptyQuery() {
        //Given
        String urlQuery = "";

        //When
        Map<String, List<String>> params = ApiUtils.splitQuery(urlQuery);

        //Then
        Assertions.assertTrue(params.isEmpty());
    }

    @Test
    void convertWithIteration() {
        //Given
        LinkedHashMap<String, Integer> map = Map.of("k1", 1, "k2", 2).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        //When
        String toString = ApiUtils.convertWithIteration(map);

        Assertions.assertNotNull(toString);
        Assertions.assertFalse(toString.isEmpty());
        Assertions.assertEquals(toString,"k1=1, k2=2");
    }

    @Test
    void convertWithIterationForEmptyMap() {
        //Given
        Map<String, Integer> emptyMap = Map.of();
        LinkedHashMap<String, Integer> map = emptyMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        //When
        String toString = ApiUtils.convertWithIteration(map);

        Assertions.assertNotNull(toString);
        Assertions.assertTrue(toString.isEmpty());
    }
}