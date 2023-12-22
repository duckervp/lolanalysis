package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.dto.MatchDto;

import java.util.List;

public interface MatchService {
    List<String> crawlMatchIds(String puuid, Long startTime, Long endTime, Integer queue, String type, Integer start, Integer count);

    MatchDto crawlMatchById(String matchId);
}
