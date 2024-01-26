package com.ducker.lolanalysis.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantIndexStatisticResponse {
    private Long totalMatches;
    private Long totalWin;
    private Long totalLost;
    private Long totalKills;
    private Long totalAssists;
    private Long totalDeaths;
    private Long totalGoldEarned;
    private Long totalDamageDealt;
    private Long totalDamageTaken;
    private Long totalMinionsKilled;
    private Long totalTimeSpentDead;
    private Long totalTurretKills;
    private Long totalTurretTakedowns;
    private Long totalInhibitorKills;
    private Long totalInhibitorTakedowns;
    private Long totalNexusKills;
    private Long totalNexusTakedowns;
    private Long totalFirstBloodKills;
    private Long totalDoubleKills;
    private Long totalTripleKills;
    private Long totalQuadraKills;
    private Long totalPentaKills;
    private Long totalUniqueChampPlayed;
}
