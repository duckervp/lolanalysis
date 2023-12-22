package com.ducker.lolanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PerkStyleDto {
    private String description;
    private List<PerkStyleSelectionDto> selections;
    private int style;
}
