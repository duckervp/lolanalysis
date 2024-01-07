package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.dto.MatchDto;
import com.ducker.lolanalysis.dto.request.MatchRequestParam;
import com.ducker.lolanalysis.service.HttpService;
import com.ducker.lolanalysis.service.RiotMatchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiotMatchServiceImpl implements RiotMatchService {
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final HttpService httpService;

    @Override
    @SneakyThrows
    @Cacheable(cacheNames = "matchIds")
    public List<String> crawlMatchIds(MatchRequestParam requestParam) {
        Map<String, String> paramMap = getStringStringMap(requestParam);

        HttpHeaders httpHeaders = httpService.prepareHeaders(new HashMap<>());
        String queryString = httpService.buildQueryString(paramMap);
        HttpEntity<Object> request = new HttpEntity<>(httpHeaders);
        String url = httpService.prepareMatchUrl("/lol/match/v5/matches/by-puuid/"+ requestParam.getPuuid() +"/ids" + queryString);
        ResponseEntity<String[]> matchIdsResponse = restTemplate.exchange(url, HttpMethod.GET, request, String[].class);
        return List.of(Objects.requireNonNull(matchIdsResponse.getBody()));
    }

    @Override
    @Cacheable(cacheNames = "match", key = "#matchId")
    public MatchDto crawlMatchById(String matchId) {
        HttpEntity<Object> request = new HttpEntity<>(httpService.prepareHeaders(new HashMap<>()));
        String url = httpService.prepareMatchUrl("/lol/match/v5/matches/" + matchId);
        ResponseEntity<MatchDto> matchDtoResponseEntity = restTemplate.exchange(url, HttpMethod.GET, request, MatchDto.class);
        return matchDtoResponseEntity.getBody();
    }

    private static Map<String, String> getStringStringMap(MatchRequestParam requestParam) {
        Map<String, String> paramMap = new HashMap<>();

        if (Objects.nonNull(requestParam.getStart())) {
            paramMap.put("start", requestParam.getStart().toString());
        } else {
            paramMap.put("start", "0");
        }

        if (Objects.nonNull(requestParam.getCount())) {
            paramMap.put("count", requestParam.getCount().toString());
        } else {
            paramMap.put("count", "5");
        }

        if (Objects.nonNull(requestParam.getStartTime())) {
            paramMap.put("startTime", requestParam.getStartTime().toString());
        }

        if (Objects.nonNull(requestParam.getEndTime())) {
            paramMap.put("endTime", requestParam.getEndTime().toString());
        }

        if (Objects.nonNull(requestParam.getQueue())) {
            paramMap.put("queue", requestParam.getQueue().toString());
        }

        if (Objects.nonNull(requestParam.getType())) {
            paramMap.put("type", requestParam.getType());
        }
        return paramMap;
    }
}
