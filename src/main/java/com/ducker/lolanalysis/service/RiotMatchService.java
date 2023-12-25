package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.dto.MatchDto;
import com.ducker.lolanalysis.dto.request.MatchRequestParam;

import java.util.List;

public interface RiotMatchService {
    List<String> crawlMatchIds(final MatchRequestParam requestParam);

    MatchDto crawlMatchById(String matchId);
}
