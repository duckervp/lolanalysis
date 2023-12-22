package com.ducker.lolanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PerksDto {
    private PerkStatsDto statPerks;
    private List<PerkStyleDto> styles;
}
