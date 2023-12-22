package com.ducker.lolanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObjectivesDto {
    private ObjectiveDto baron;
    private ObjectiveDto champion;
    private ObjectiveDto dragon;
    private ObjectiveDto inhibitor;
    private ObjectiveDto riftHerald;
    private ObjectiveDto tower;
}
