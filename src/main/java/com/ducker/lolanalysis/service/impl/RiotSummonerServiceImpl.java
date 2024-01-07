package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.dto.MasteryDto;
import com.ducker.lolanalysis.dto.SummonerDto;
import com.ducker.lolanalysis.service.HttpService;
import com.ducker.lolanalysis.service.RiotSummonerService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    public List<MasteryDto> findChampionMastery(String puuid) {
        HttpHeaders httpHeaders = httpService.prepareHeaders(new HashMap<>());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        String url = httpService.prepareSummonerUrl("/lol/champion-mastery/v4/champion-masteries/by-puuid/" + puuid);
        ResponseEntity<MasteryDto[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, MasteryDto[].class);
        return List.of(Objects.requireNonNull(responseEntity.getBody()));
    }
}
