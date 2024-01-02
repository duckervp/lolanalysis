package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.dto.MatchDto;

import java.util.List;

public interface MatchService {
    void save(MatchDto matchDto);

    MatchDto findByMatchId(String matchId);

    List<MatchDto> findByMatchIds(List<String> matchIds);
}
