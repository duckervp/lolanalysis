package com.ducker.lolanalysis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class HttpService {
    @Value("${riotGames.apiKey}")
    private String riotGamesApiKey = "RGAPI-7a5d2166-6b6d-49a3-a21f-e9da43735c7c";

    public String prepareUrl(String apiPath) {
        log.info("Prepare Url for {}", apiPath);
        String slash = "/";
        if (!apiPath.startsWith(slash)) {
            apiPath = slash + apiPath;
        }
        return "https://sea.api.riotgames.com" + apiPath;
    }

    public String prepareAccountUrl(String apiPath) {
        log.info("Prepare Url for {}", apiPath);
        String slash = "/";
        if (!apiPath.startsWith(slash)) {
            apiPath = slash + apiPath;
        }
        return "https://asia.api.riotgames.com" + apiPath;
    }

    public HttpHeaders prepareHeaders(Map<String, String> headerMap) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Riot-Token", riotGamesApiKey);
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
