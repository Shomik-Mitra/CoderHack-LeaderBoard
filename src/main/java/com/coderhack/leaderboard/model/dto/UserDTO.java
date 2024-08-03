package com.coderhack.leaderboard.model.dto;

import com.coderhack.leaderboard.model.Badges;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String userId;

    private String userName;

    private int score;

    private Set<Badges> badges;
}
