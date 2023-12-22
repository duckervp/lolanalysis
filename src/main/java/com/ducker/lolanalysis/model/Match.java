package com.ducker.lolanalysis.model;

import com.ducker.lolanalysis.dto.ParticipantDto;
import com.ducker.lolanalysis.dto.TeamDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
@AllArgsConstructor
@Builder
public class Match {
    @Id
    private String matchId;
    private String dataVersion;
    private long gameCreation;
    private long gameDuration;
    private long gameEndTimestamp;
    private long gameId;
    private String gameMode;
    private String gameName;
    private long gameStartTimestamp;
    private String gameType;
    private String gameVersion;
    private int mapId;
    private String platformId;
    private int queueId;
    private String tournamentCode;
}
