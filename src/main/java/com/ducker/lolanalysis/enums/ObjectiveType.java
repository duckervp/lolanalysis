package com.ducker.lolanalysis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum ObjectiveType {
    BARON("baron"),
    CHAMPION("champion"),
    DRAGON("dragon"),
    INHIBITOR("inhibitor"),
    RIFT_HERALD("riftHerald"),
    TOWER("tower");

    private final String value;

    @JsonValue // convert enum to value on response class
    public String getValue() {
        return value;
    }

    @JsonCreator // convert value to enum on request class
    public static ObjectiveType fromValue(String value) {
        for (ObjectiveType name : ObjectiveType.values()) {
            if (name.value.equalsIgnoreCase(value)) {
                return name;
            }
        }
        log.error("Invalid ObjectiveType value: {}", value);
        return null;
    }
}
