package com.bahar.gamescore.data;


import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InmemoryScoreRepository {
    private static final Map<String, Map<String, Score>> SCORE_STORE = new ConcurrentHashMap();

    public void create(Score score) {

        if (!SCORE_STORE.containsKey(score.getLevelIdentifier())) {
            SCORE_STORE.put(score.getLevelIdentifier(), new ConcurrentHashMap());
        }

        SCORE_STORE.get(score.getLevelIdentifier()).put(score.getUserIdentifier(), score);

    }

    public LinkedHashMap<String, Integer> getScoresByDescendingOrder(String levelIdentifier, int limit) {
        Map<String, Score> userMap =SCORE_STORE.getOrDefault(levelIdentifier,Map.of());
        Map<String, Integer> userScoreMap = userMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> e.getValue().getScore()));

        LinkedHashMap<String, Integer> sortedMap = userScoreMap.entrySet().stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return sortedMap;

    }
}
