package com.ducker.lolanalysis.rest;

import com.ducker.lolanalysis.dto.MatchDto;
import com.ducker.lolanalysis.dto.response.Response;
import com.ducker.lolanalysis.service.MatchService;
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
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/{matchId}")
    public ResponseEntity<Response<MatchDto>> findMatchById(@PathVariable String matchId) {
        Response<MatchDto> response = Response.with(
                HttpStatus.OK.value(),
                "Found LOL Match Successfully",
                matchService.findByMatchId(matchId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<Response<List<MatchDto>>> findMatchById(@RequestParam List<String> matchIds) {
        Response<List<MatchDto>> response = Response.with(
                HttpStatus.OK.value(),
                "Found LOL Matches Successfully",
                matchService.findByMatchIds(matchIds));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<MatchDto>>> findMatches(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Response<List<MatchDto>> response = Response.with(
                HttpStatus.OK.value(),
                "Found LOL Matches Successfully",
                matchService.findMatches(pageNo, pageSize));
        return ResponseEntity.ok(response);
    }
}
