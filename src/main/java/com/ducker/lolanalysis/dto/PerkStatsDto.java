package com.ducker.lolanalysis.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerkStatsDto {
    private int defense;
    private int flex;
    private int offense;
}
