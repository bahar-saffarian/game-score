package com.bahar.gamescore.api;

import com.bahar.gamescore.util.ApiUtils;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.LinkedHashMap;

@Value
@AllArgsConstructor
public class HighScoreResponse {
    LinkedHashMap<String, Integer> scores;

    @Override
    public String toString() {
        return ApiUtils.convertWithIteration(scores);
    }
}
