package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.dto.response.ParticipantIndexStatisticResponse;

public interface StatisticService {
    ParticipantIndexStatisticResponse getLastXMatchStatistic(String puuid, Integer numberOfMatch);
}
