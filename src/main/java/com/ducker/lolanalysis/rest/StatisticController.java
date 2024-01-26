package com.ducker.lolanalysis.rest;

import com.ducker.lolanalysis.dto.response.ParticipantIndexStatisticResponse;
import com.ducker.lolanalysis.dto.response.Response;
import com.ducker.lolanalysis.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/{puuid}/indexes")
    public ResponseEntity<Response<ParticipantIndexStatisticResponse>> findMatchById(@PathVariable String puuid, @RequestParam Integer count) {
        Response<ParticipantIndexStatisticResponse> response = Response.with(
                HttpStatus.OK.value(),
                "Found Statistic Successfully",
                statisticService.getLastXMatchStatistic(puuid, count));
        return ResponseEntity.ok(response);
    }

}
