package com.bahar.gamescore.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ServiceNameTest {

    @Test
    void loginServiceNameFromString() {
        //Given
        String serviceNameValue = "login";

        //When
        ServiceName serviceName = ServiceName.fromString(serviceNameValue);

        //Then
        Assertions.assertNotNull(serviceName);
        Assertions.assertEquals(serviceName, ServiceName.LOGIN);
    }

    @Test
    void scoreServiceNameFromString() {
        //Given
        String serviceNameValue = "score";

        //When
        ServiceName serviceName = ServiceName.fromString(serviceNameValue);

        //Then
        Assertions.assertNotNull(serviceName);
        Assertions.assertEquals(serviceName, ServiceName.SCORE);
    }

    @Test
    void highScoreListServiceNameFromString() {
        //Given
        String serviceNameValue = "highscorelist";

        //When
        ServiceName serviceName = ServiceName.fromString(serviceNameValue);

        //Then
        Assertions.assertNotNull(serviceName);
        Assertions.assertEquals(serviceName, ServiceName.HIGH_SCORE_LIST);
    }

    @Test
    void NotExistServiceNameFromString() {
        //Given
        String serviceNameValue = "xyz";

        //When
        ServiceName serviceName = ServiceName.fromString(serviceNameValue);

        //Then
        Assertions.assertNull(serviceName);
    }
}