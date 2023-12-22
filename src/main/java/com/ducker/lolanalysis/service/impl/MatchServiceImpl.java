package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.dto.InfoDto;
import com.ducker.lolanalysis.dto.MatchDto;
import com.ducker.lolanalysis.model.Match;
import com.ducker.lolanalysis.model.MatchLookup;
import com.ducker.lolanalysis.model.Team;
import com.ducker.lolanalysis.repository.MatchLookupRepository;
import com.ducker.lolanalysis.service.MatchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchServiceImpl implements MatchService {
    private final RestTemplate restTemplate;

    @Value("${riotGames.apiKey}")
    private String riotGamesApiKey = "RGAPI-7a5d2166-6b6d-49a3-a21f-e9da43735c7c";

    private final ObjectMapper objectMapper;

    private final MatchLookupRepository matchLookupRepository;

    private String prepareUrl(String apiPath) {
        String slash = "/";
        if (!apiPath.startsWith(slash)) {
            apiPath = slash + apiPath;
        }
        return "https://sea.api.riotgames.com" + apiPath;
    }

    private HttpHeaders prepareHeaders(Map<String, String> headerMap) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Riot-Token", riotGamesApiKey);
        headerMap.forEach(httpHeaders::add);
        return httpHeaders;
    }

    private String buildQueryString(Map<String, String> paramMap) {
        if (paramMap.isEmpty()) {
            return "";
        } else {
            StringBuilder result = new StringBuilder("?");
            paramMap.forEach((key, value) -> result.append(String.format("%s=%s&", key, value)));
            result.deleteCharAt(result.lastIndexOf("&"));
            return result.toString();
        }
    }

    @Override
    @SneakyThrows
    public List<String> crawlMatchIds(String puuid, Long startTime, Long endTime, Integer queue, String type, Integer start, Integer count) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("start", start.toString());
        paramMap.put("count", count.toString());

        if (Objects.nonNull(startTime)) {
            paramMap.put("startTime", startTime.toString());
        }

        if (Objects.nonNull(endTime)) {
            paramMap.put("endTime", endTime.toString());
        }

        if (Objects.nonNull(queue)) {
            paramMap.put("queue", queue.toString());
        }

        if (Objects.nonNull(type)) {
            paramMap.put("type", type);
        }

        HttpHeaders httpHeaders = prepareHeaders(new HashMap<>());
        String queryString = buildQueryString(paramMap);
        HttpEntity<Object> request = new HttpEntity<>(httpHeaders);
        String url = prepareUrl("/lol/match/v5/matches/by-puuid/"+ puuid +"/ids" + queryString);
        TypeReference<List<String>> listString = new TypeReference<>() {};
        ResponseEntity<String> matchIdsResponse = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return objectMapper.readValue(matchIdsResponse.getBody(), listString);
    }

    @Override
    public MatchDto crawlMatchById(String matchId) {
        HttpEntity<Object> request = new HttpEntity<>(prepareHeaders(new HashMap<>()));
        String url = prepareUrl("/lol/match/v5/matches/" + matchId);
        ResponseEntity<MatchDto> matchDtoResponseEntity = restTemplate.exchange(url, HttpMethod.GET, request, MatchDto.class);
        log.info("{}", matchDtoResponseEntity.getBody());
        return null;
    }

    private void temp() {
        String puuid = "miyF5bYOgWve2J82vsSgzA6BdMg2FV4rf6V4xXCtkxV5Z0gJYcV_XpzgD5eQ68NjB7IY85pC6PLF1Q";
        List<String> matchIds = crawlMatchIds(puuid, null, null, null, null, 0, 20);
        List<String> existedMatchIds = matchLookupRepository.findByMatchIn(matchIds).stream().map(MatchLookup::getMatch).toList();
        List<String> newMatchIds = matchIds.stream().filter(match -> !existedMatchIds.contains(match)).toList();
        List<MatchLookup> matchLookupToSaveList = new ArrayList<>();
        newMatchIds.forEach(matchId -> {
            MatchLookup matchLookup = new MatchLookup(matchId);
            matchLookupToSaveList.add(matchLookup);
            MatchDto matchDto = crawlMatchById(matchId);

            InfoDto matchInfo = matchDto.getInfo();

            Match match = Match.builder()
                    .dataVersion(matchDto.getMetadata().getDataVersion())
                    .gameCreation(matchInfo.getGameCreation())
                    .gameDuration(matchInfo.getGameDuration())
                    .gameEndTimestamp(matchInfo.getGameEndTimestamp())
                    .gameId(matchInfo.getGameId())
                    .gameMode(matchInfo.getGameMode())
                    .gameName(matchInfo.getGameName())
                    .gameStartTimestamp(matchInfo.getGameStartTimestamp())
                    .gameType(matchInfo.getGameType())
                    .gameVersion(matchInfo.getGameVersion())
                    .mapId(matchInfo.getMapId())
                    .platformId(matchInfo.getPlatformId())
                    .queueId(matchInfo.getQueueId())
                    .tournamentCode(matchInfo.getTournamentCode())
                    .build();

            List<Team> teams = new ArrayList<>();
            matchDto.getInfo().getTeams().forEach(teamDto -> {
                Team team = Team.builder()
                        .win(teamDto.isWin())
                        .matchId(match.getMatchId())
                        .build();
                teams.add(team);
            });

        });
        matchLookupRepository.saveAll(matchLookupToSaveList);

    }

//    public static void main(String[] args) {
//        MatchServiceImpl matchService = new MatchServiceImpl(new RestTemplate(), new ObjectMapper());
//        String puuid = "miyF5bYOgWve2J82vsSgzA6BdMg2FV4rf6V4xXCtkxV5Z0gJYcV_XpzgD5eQ68NjB7IY85pC6PLF1Q";
//        matchService.crawlMatchIds(puuid, null, null, null, null, 0, 20);
//        matchService.crawlMatchById("VN2_273459672");
//    }
}
