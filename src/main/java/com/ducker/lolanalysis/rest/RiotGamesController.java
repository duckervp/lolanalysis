package com.ducker.lolanalysis.rest;

import com.ducker.lolanalysis.dto.MatchDto;
import com.ducker.lolanalysis.dto.request.MatchRequestParam;
import com.ducker.lolanalysis.dto.response.Response;
import com.ducker.lolanalysis.model.Account;
import com.ducker.lolanalysis.service.RiotAccountService;
import com.ducker.lolanalysis.service.RiotMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("riot/")
public class RiotGamesController {
    private final RiotAccountService riotAccountService;

    private final RiotMatchService riotMatchService;

    @GetMapping("/accounts")
    public ResponseEntity<Response<Account>> findAccount(@RequestParam String gameName, @RequestParam String tagLine) {
        Response<Account> response = Response.with(
                HttpStatus.OK.value(),
                "Found Riot Account Successfully",
                riotAccountService.findAccount(gameName, tagLine));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/{puuid}")
    public ResponseEntity<Response<Account>> findAccount(@PathVariable String puuid) {
        Response<Account> response = Response.with(
                HttpStatus.OK.value(),
                "Found Riot Account Successfully",
                riotAccountService.findAccount(puuid));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lol/matches")
    public ResponseEntity<Response<List<String>>> findMatches(final MatchRequestParam requestParam) {
        Response<List<String>> response = Response.with(
                HttpStatus.OK.value(),
                "Found LOL Matches Successfully",
                riotMatchService.crawlMatchIds(requestParam));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lol/matches/{matchId}")
    public ResponseEntity<Response<MatchDto>> findMatchById(@PathVariable String matchId) {
        Response<MatchDto> response = Response.with(
                HttpStatus.OK.value(),
                "Found LOL Match Successfully",
                riotMatchService.crawlMatchById(matchId));
        return ResponseEntity.ok(response);
    }

}
