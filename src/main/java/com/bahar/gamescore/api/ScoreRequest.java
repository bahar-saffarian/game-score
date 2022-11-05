package com.bahar.gamescore.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRequest {
    private int score;
}
