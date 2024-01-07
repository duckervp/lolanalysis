package com.ducker.lolanalysis.rest;

import com.ducker.lolanalysis.dto.MatchDto;
import com.ducker.lolanalysis.dto.response.Response;
import com.ducker.lolanalysis.model.Account;
import com.ducker.lolanalysis.service.AccountService;
import com.ducker.lolanalysis.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Response<Account>> findAccount(@RequestParam String gameName, @RequestParam String tagLine) {
        Response<Account> response = Response.with(
                HttpStatus.OK.value(),
                "Found Riot Account Successfully",
                accountService.find(gameName, tagLine));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{puuid}")
    public ResponseEntity<Response<Account>> findAccount(@PathVariable String puuid) {
        Response<Account> response = Response.with(
                HttpStatus.OK.value(),
                "Found Riot Account Successfully",
                accountService.find(puuid));
        return ResponseEntity.ok(response);
    }
}
