package com.bahar.gamescore.service;

import com.bahar.gamescore.data.InmemoryScoreRepository;
import com.bahar.gamescore.data.Score;
import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;

@AllArgsConstructor
public class ScoreService {
    final private InmemoryScoreRepository scoreRepository;
    public void saveScore(String userIdentifier, String levelIdentifier,  int score) {
        scoreRepository.create(
                Score.builder()
                        .levelIdentifier(levelIdentifier)
                        .userIdentifier(userIdentifier)
                        .score(score)
                        .build()
        );
    }

    public LinkedHashMap<String, Integer> getHighScores(String levelIdentifier) {
        return scoreRepository.getScoresByDescendingOrder(levelIdentifier, 15);
    }
}
