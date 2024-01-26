package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.dto.MatchDto;
import com.ducker.lolanalysis.dto.ParticipantDto;
import com.ducker.lolanalysis.dto.request.MatchRequestParam;
import com.ducker.lolanalysis.dto.response.ParticipantIndexStatisticResponse;
import com.ducker.lolanalysis.service.MatchService;
import com.ducker.lolanalysis.service.RiotMatchService;
import com.ducker.lolanalysis.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {
    private final RiotMatchService riotMatchService;

    private final MatchService matchService;

    @Override
    public ParticipantIndexStatisticResponse getLastXMatchStatistic(String puuid, Integer numberOfMatch) {
        List<String> matchIds = riotMatchService.crawlMatchIds(MatchRequestParam.builder()
                .puuid(puuid)
                .count(numberOfMatch)
                .build());

        List<MatchDto> matchDtoList = matchService.findByMatchIds(matchIds);

        long totalMatches = matchDtoList.size();
        long totalWin = 0L;
        long totalLost = 0L;
        long totalKills = 0L;
        long totalAssists = 0L;
        long totalDeaths = 0L;
        long totalGoldEarned = 0L;
        long totalDamageDealt = 0L;
        long totalDamageTaken = 0L;
        long totalMinionsKilled = 0L;
        long totalTimeSpentDead = 0L;
        long totalTurretKills = 0L;
        long totalTurretTakedowns = 0L;
        long totalInhibitorKills = 0L;
        long totalInhibitorTakedowns = 0L;
        long totalNexusKills = 0L;
        long totalNexusTakedowns = 0L;
        long totalFirstBloodKills = 0L;
        long totalDoubleKills = 0L;
        long totalTripleKills = 0L;
        long totalQuadraKills = 0L;
        long totalPentaKills = 0L;

        Map<Integer, Integer> champCountMap = new HashMap<>();

        for (MatchDto matchDto : matchDtoList) {
            ParticipantDto participantDto = matchDto.getInfo().getParticipants().stream()
                    .filter(p -> p.getPuuid().equals(puuid)).findAny().orElse(null);

            if (Objects.isNull(participantDto)) {
                continue;
            }

            if (participantDto.isWin()) {
                totalWin += 1;
            } else {
                totalLost += 1;
            }

            totalKills += participantDto.getKills();
            totalAssists += participantDto.getAssists();
            totalDeaths += participantDto.getDeaths();
            totalGoldEarned += participantDto.getGoldEarned();
            totalDamageDealt += participantDto.getTotalDamageDealt();
            totalDamageTaken += participantDto.getTotalDamageTaken();
            totalMinionsKilled += participantDto.getTotalMinionsKilled();
            totalTimeSpentDead += participantDto.getTotalTimeSpentDead();
            totalTurretKills += participantDto.getTurretKills();
            totalTurretTakedowns += participantDto.getTurretTakedowns();
            totalInhibitorKills += participantDto.getInhibitorKills();
            totalInhibitorTakedowns += participantDto.getInhibitorTakedowns();
            totalNexusKills += participantDto.getNexusKills();
            totalNexusTakedowns += participantDto.getNexusTakedowns();
            totalFirstBloodKills += participantDto.isFirstBloodKill() ? 1 : 0;
            totalDoubleKills += participantDto.getDoubleKills();
            totalTripleKills += participantDto.getTripleKills();
            totalQuadraKills += participantDto.getQuadraKills();
            totalPentaKills += participantDto.getPentaKills();

            if (champCountMap.containsKey(participantDto.getChampionId())) {
                int count = champCountMap.get(participantDto.getChampionId());
                champCountMap.put(participantDto.getChampionId(), count + 1);
            } else {
                champCountMap.put(participantDto.getChampionId(), 1);
            }
        }

        long totalUniqueChampPlayed = champCountMap.keySet().size();

        return ParticipantIndexStatisticResponse.builder()
                .totalMatches(totalMatches)
                .totalWin(totalWin)
                .totalLost(totalLost)
                .totalKills(totalKills)
                .totalAssists(totalAssists)
                .totalDeaths(totalDeaths)
                .totalGoldEarned(totalGoldEarned)
                .totalDamageDealt(totalDamageDealt)
                .totalDamageTaken(totalDamageTaken)
                .totalMinionsKilled(totalMinionsKilled)
                .totalTimeSpentDead(totalTimeSpentDead)
                .totalTurretKills(totalTurretKills)
                .totalTurretTakedowns(totalTurretTakedowns)
                .totalInhibitorKills(totalInhibitorKills)
                .totalInhibitorTakedowns(totalInhibitorTakedowns)
                .totalNexusKills(totalNexusKills)
                .totalNexusTakedowns(totalNexusTakedowns)
                .totalFirstBloodKills(totalFirstBloodKills)
                .totalDoubleKills(totalDoubleKills)
                .totalTripleKills(totalTripleKills)
                .totalQuadraKills(totalQuadraKills)
                .totalPentaKills(totalPentaKills)
                .totalUniqueChampPlayed(totalUniqueChampPlayed)
                .build();
    }
}
