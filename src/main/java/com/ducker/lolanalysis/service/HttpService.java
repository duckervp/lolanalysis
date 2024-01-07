package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.enums.RiotPlatformRouting;
import com.ducker.lolanalysis.enums.RiotRegionalRouting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class HttpService {
    @Value("${riotGames.apiKey}")
    private String riotGamesApiKey;

    public String prepareUrl(String routingHost, String apiPath) {
        String slash = "/";
        if (!apiPath.startsWith(slash)) {
            apiPath = slash + apiPath;
        }

        String url = "https://" + routingHost + apiPath;

        log.info("Prepare Url: {}", url);

        return url;
    }

    public String prepareMatchUrl(String apiPath) {
        return prepareUrl(RiotRegionalRouting.SEA.getHost(), apiPath);
    }

    public String prepareAccountUrl(String apiPath) {
        return prepareUrl(RiotRegionalRouting.ASIA.getHost(), apiPath);
    }

    public String prepareSummonerUrl(String apiPath) {
        return prepareUrl(RiotPlatformRouting.VN2.getHost(), apiPath);
    }

    public HttpHeaders prepareHeaders(Map<String, String> headerMap) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String riotTokenHeader = "X-Riot-Token";
        httpHeaders.add(riotTokenHeader, riotGamesApiKey);
        headerMap.forEach(httpHeaders::add);
        return httpHeaders;
    }

    public String buildQueryString(Map<String, String> paramMap) {
        if (paramMap.isEmpty()) {
            return "";
        } else {
            StringBuilder result = new StringBuilder("?");
            paramMap.forEach((key, value) -> result.append(String.format("%s=%s&", key, value)));
            result.deleteCharAt(result.lastIndexOf("&"));
            return result.toString();
        }
    }
}
