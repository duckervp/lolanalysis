package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.dto.MasteryDto;
import com.ducker.lolanalysis.dto.SummonerDto;
import com.ducker.lolanalysis.service.HttpService;
import com.ducker.lolanalysis.service.RiotSummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RiotSummonerServiceImpl implements RiotSummonerService {

    private final HttpService httpService;

    private final RestTemplate restTemplate;

    @Override
    public SummonerDto findSummoner(String puuid) {
        HttpHeaders httpHeaders = httpService.prepareHeaders(new HashMap<>());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        String url = httpService.prepareSummonerUrl("/lol/summoner/v4/summoners/by-puuid/" + puuid);
        ResponseEntity<SummonerDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SummonerDto.class);
        return responseEntity.getBody();
    }

    @Override
    public List<MasteryDto> findChampionMastery(String puuid, Boolean orderByLevel, Integer count) {
        HttpHeaders httpHeaders = httpService.prepareHeaders(new HashMap<>());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        String url = httpService.prepareSummonerUrl("/lol/champion-mastery/v4/champion-masteries/by-puuid/" + puuid);
        ResponseEntity<MasteryDto[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, MasteryDto[].class);
        List<MasteryDto> masteryDtoList = List.of(Objects.requireNonNull(responseEntity.getBody()));
        if (Boolean.TRUE.equals(orderByLevel)) {
            masteryDtoList = masteryDtoList.stream().sorted(
                    Comparator.comparing(MasteryDto::getChampionLevel, Comparator.reverseOrder())
                            .thenComparing(MasteryDto::getChampionPoints, Comparator.reverseOrder())).toList();
        }
        if (Objects.nonNull(count) && count > 0 && count < masteryDtoList.size()) {
            return masteryDtoList.subList(0, count);
        }
        return masteryDtoList;
    }
}
