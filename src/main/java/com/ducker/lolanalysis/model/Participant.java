package com.ducker.lolanalysis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matchId; // Match id PK
    private String puuid; // Account id PK
    private Long perkId; // Perk id PK
    private int assists;
    private int baronKills;
    private int bountyLevel;
    private int champExperience;
    private int champLevel;
    private int championId; // Consider championName for matches prior to patch 11.4
    private String championName; // Consider for matches prior to patch 11.4
    private int championTransform;
    private int consumablesPurchased;
    private int damageDealtToBuildings;
    private int damageDealtToObjectives;
    private int damageDealtToTurrets;
    private int damageSelfMitigated;
    private int deaths;
    private int detectorWardsPlaced;
    private int doubleKills;
    private int dragonKills;
    private boolean firstBloodAssist;
    private boolean firstBloodKill;
    private boolean firstTowerAssist;
    private boolean firstTowerKill;
    private boolean gameEndedInEarlySurrender;
    private boolean gameEndedInSurrender;
    private int goldEarned;
    private int goldSpent;
    private String individualPosition;
    private int inhibitorKills;
    private int inhibitorTakedowns;
    private int inhibitorsLost;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private int itemsPurchased;
    private int killingSprees;
    private int kills;
    private String lane;
    private int largestCriticalStrike;
    private int largestKillingSpree;
    private int largestMultiKill;
    private int longestTimeSpentLiving;
    private int magicDamageDealt;
    private int magicDamageDealtToChampions;
    private int magicDamageTaken;
    private int neutralMinionsKilled;
    private int nexusKills;
    private int nexusTakedowns;
    private int nexusLost;
    private int objectivesStolen;
    private int objectivesStolenAssists;
    private int participantId;
    private int pentaKills;
    private int physicalDamageDealt;
    private int physicalDamageDealtToChampions;
    private int physicalDamageTaken;
    private int profileIcon;
    private int quadraKills;
    private String riotIdName;
    private String riotIdTagline;
    private String role;
    private int sightWardsBoughtInGame;
    private int spell1Casts;
    private int spell2Casts;
    private int spell3Casts;
    private int spell4Casts;
    private int summoner1Casts;
    private int summoner1Id;
    private int summoner2Casts;
    private int summoner2Id;
    private String summonerId;
    private int summonerLevel;
    private String summonerName;
    private boolean teamEarlySurrendered;
    private int teamId;
    private String teamPosition;
    private int timeCCingOthers;
    private int timePlayed;
    private int totalDamageDealt;
    private int totalDamageDealtToChampions;
    private int totalDamageShieldedOnTeammates;
    private int totalDamageTaken;
    private int totalHeal;
    private int totalHealsOnTeammates;
    private int totalMinionsKilled;
    private int totalTimeCCDealt;
    private int totalTimeSpentDead;
    private int totalUnitsHealed;
    private int tripleKills;
    private int trueDamageDealt;
    private int trueDamageDealtToChampions;
    private int trueDamageTaken;
    private int turretKills;
    private int turretTakedowns;
    private int turretsLost;
    private int unrealKills;
    private int visionScore;
    private int visionWardsBoughtInGame;
    private int wardsKilled;
    private int wardsPlaced;
    private boolean win;
}
