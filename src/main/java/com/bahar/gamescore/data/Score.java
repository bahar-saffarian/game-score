package com.bahar.gamescore.data;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Score {
    String userIdentifier;
    String levelIdentifier;
    int score;
}
