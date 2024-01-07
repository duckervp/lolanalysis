package com.ducker.lolanalysis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum PerkStyle {
    PRIMARY_TYPE("primaryStyle"),
    SUB_TYPE("subStyle");

    private final String value;

    @JsonValue // convert enum to value on response class
    public String getValue() {
        return value;
    }

    @JsonCreator // convert value to enum on request class
    public static PerkStyle fromValue(String value) {
        for (PerkStyle name : PerkStyle.values()) {
            if (name.value.equalsIgnoreCase(value)) {
                return name;
            }
        }
        log.error("Invalid PerkType value: {}", value);
        return null;
    }
}
