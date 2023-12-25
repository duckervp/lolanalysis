package com.ducker.lolanalysis.config;

import com.ducker.lolanalysis.exception.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RestTemplateConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate =  new RestTemplate();
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ResponseErrorHandler restTemplateResponseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(@NonNull ClientHttpResponse response) throws IOException {
                return response.getStatusCode() != HttpStatus.OK;
            }

            @Override
            public void handleError(@NonNull ClientHttpResponse response) throws IOException {
                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    String result = new BufferedReader(new InputStreamReader(response.getBody()))
                            .lines().collect(Collectors.joining("\n"));
                    TypeReference<Map<String, Map<String, Object>>> typeReference = new TypeReference<>() {

                    };

                    Map<String, Map<String, Object>> resultMap = objectMapper.readValue(result, typeReference);
                    log.info("Rest Error: {}", resultMap);

                    Map<String, Object> status = resultMap.get("status");
                    String message = response.getStatusText();
                    if (Objects.nonNull(status)) {
                        message = (String) status.get("message");
                    }

                    throw new NotFoundException(message);
                }
            }
        };
    }
}
