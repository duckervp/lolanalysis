package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.model.Account;
import com.ducker.lolanalysis.service.HttpService;
import com.ducker.lolanalysis.service.RiotAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RiotAccountServiceImpl implements RiotAccountService {
    private final HttpService httpService;

    private final RestTemplate restTemplate;

    @Override
    @Cacheable(cacheNames = "account")
    public Account findAccount(String gameName, String tagLine) {
        HttpHeaders httpHeaders = httpService.prepareHeaders(new HashMap<>());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        String url = httpService.prepareAccountUrl("/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);
        ResponseEntity<Account> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Account.class);
        return responseEntity.getBody();
    }

    @Override
    @Cacheable(cacheNames = "account", key = "#puuid")
    public Account findAccount(String puuid) {
        HttpHeaders httpHeaders = httpService.prepareHeaders(new HashMap<>());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        String url = httpService.prepareAccountUrl("/riot/account/v1/accounts/by-puuid/" + puuid);
        ResponseEntity<Account> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Account.class);
        return responseEntity.getBody();
    }
}
