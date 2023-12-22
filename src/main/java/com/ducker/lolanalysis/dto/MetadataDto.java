package com.ducker.lolanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MetadataDto {
    private String dataVersion;
    private String matchId;
    private List<String> participants;
}
