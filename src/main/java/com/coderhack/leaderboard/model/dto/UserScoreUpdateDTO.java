package com.coderhack.leaderboard.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreUpdateDTO {

    @NotNull
    @Min(value = 1,message = "Score must be at least greater than 0")
    @Max(value = 100, message = "Score must be at most 100")
    private Integer score;
}
