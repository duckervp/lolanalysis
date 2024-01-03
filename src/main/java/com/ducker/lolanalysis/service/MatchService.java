package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.dto.MatchDto;

import java.util.List;

public interface MatchService {
    void saveAll(List<MatchDto> matchDtoList);

    void save(MatchDto matchDto);

    MatchDto findByMatchId(String matchId);

    List<MatchDto> findByMatchIds(List<String> matchIds);

    List<MatchDto> findMatches(Integer pageNo, Integer pageSize);
}
