package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.repository.MatchLookupRepository;

public class MatchService {
//    private final MatchLookupRepository matchLookupRepository;
    //    private void temp() {
//        String puuid = "miyF5bYOgWve2J82vsSgzA6BdMg2FV4rf6V4xXCtkxV5Z0gJYcV_XpzgD5eQ68NjB7IY85pC6PLF1Q";
//        List<String> matchIds = crawlMatchIds(puuid, null, null, null, null, 0, 20);
//        List<String> existedMatchIds = matchLookupRepository.findByMatchIn(matchIds).stream().map(MatchLookup::getMatch).toList();
//        List<String> newMatchIds = matchIds.stream().filter(match -> !existedMatchIds.contains(match)).toList();
//        List<MatchLookup> matchLookupToSaveList = new ArrayList<>();
//        newMatchIds.forEach(matchId -> {
//            MatchLookup matchLookup = new MatchLookup(matchId);
//            matchLookupToSaveList.add(matchLookup);
//            MatchDto matchDto = crawlMatchById(matchId);
//
//            InfoDto matchInfo = matchDto.getInfo();
//
//            Match match = Match.builder()
//                    .dataVersion(matchDto.getMetadata().getDataVersion())
//                    .gameCreation(matchInfo.getGameCreation())
//                    .gameDuration(matchInfo.getGameDuration())
//                    .gameEndTimestamp(matchInfo.getGameEndTimestamp())
//                    .gameId(matchInfo.getGameId())
//                    .gameMode(matchInfo.getGameMode())
//                    .gameName(matchInfo.getGameName())
//                    .gameStartTimestamp(matchInfo.getGameStartTimestamp())
//                    .gameType(matchInfo.getGameType())
//                    .gameVersion(matchInfo.getGameVersion())
//                    .mapId(matchInfo.getMapId())
//                    .platformId(matchInfo.getPlatformId())
//                    .queueId(matchInfo.getQueueId())
//                    .tournamentCode(matchInfo.getTournamentCode())
//                    .build();
//
//            List<Team> teams = new ArrayList<>();
//            matchDto.getInfo().getTeams().forEach(teamDto -> {
//                Team team = Team.builder()
//                        .win(teamDto.isWin())
//                        .matchId(match.getMatchId())
//                        .build();
//                teams.add(team);
//            });
//
//        });
//        matchLookupRepository.saveAll(matchLookupToSaveList);
//
//    }

//    public static void main(String[] args) {
//        MatchServiceImpl matchService = new MatchServiceImpl(new RestTemplate(), new ObjectMapper());
//        String puuid = "miyF5bYOgWve2J82vsSgzA6BdMg2FV4rf6V4xXCtkxV5Z0gJYcV_XpzgD5eQ68NjB7IY85pC6PLF1Q";
//        matchService.crawlMatchIds(puuid, null, null, null, null, 0, 20);
//        matchService.crawlMatchById("VN2_273459672");
//    }
}
