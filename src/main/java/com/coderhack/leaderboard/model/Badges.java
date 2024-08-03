package com.coderhack.leaderboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Badges {
    CODE_NINJA("Code Ninja"),
    CODE_CHAMP("Code Champ"),
    CODE_MASTER("Code Master");

    private final String badgeName;
}
