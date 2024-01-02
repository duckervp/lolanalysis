package com.ducker.lolanalysis.service;

public class TempService {
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
//        });
//        matchLookupRepository.saveAll(matchLookupToSaveList);
//
//    }
}
