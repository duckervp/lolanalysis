package com.ducker.lolanalysis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RiotRegionalRouting {
    AMERICAS("americas.api.riotgames.com"),
    ASIA("asia.api.riotgames.com"),
    EUROPE("europe.api.riotgames.com"),
    SEA("sea.api.riotgames.com");

    private final String host;
}
