package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.dto.MasteryDto;
import com.ducker.lolanalysis.dto.SummonerDto;

import java.util.List;

public interface RiotSummonerService {
    SummonerDto findSummoner(String puuid);

    List<MasteryDto> findChampionMastery(String puuid);
}
