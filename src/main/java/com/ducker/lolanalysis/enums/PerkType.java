package com.ducker.lolanalysis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum PerkType {
    PRIMARY_TYPE("primaryType"),
    SUB_TYPE("subType");

    private final String value;

    @JsonValue // convert enum to value on response class
    public String getValue() {
        return value;
    }

    @JsonCreator // convert value to enum on request class
    public static PerkType fromValue(String value) {
        for (PerkType name : PerkType.values()) {
            if (name.value.equalsIgnoreCase(value)) {
                return name;
            }
        }
        log.error("Invalid PerkType value: {}", value);
        return null;
    }
}
