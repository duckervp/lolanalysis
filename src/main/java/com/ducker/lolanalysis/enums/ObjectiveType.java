package com.ducker.lolanalysis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ObjectiveType {
    BARON("baron"),
    CHAMPION("champion"),
    DRAGON("dragon"),
    INHIBITOR("inhibitor"),
    RIFT_HERALD("riftHerald"),
    TOWER("tower");

    private final String value;
}
