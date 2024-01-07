package com.ducker.lolanalysis.service.impl;

import com.ducker.lolanalysis.model.Account;
import com.ducker.lolanalysis.repository.AccountRepository;
import com.ducker.lolanalysis.service.AccountService;
import com.ducker.lolanalysis.service.RiotAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final RiotAccountService riotAccountService;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account find(String gameName, String tagLine) {
        Account account = accountRepository.findByGameNameAndTagLine(gameName, tagLine).orElse(null);

        if (Objects.isNull(account)) {
            account = riotAccountService.findAccount(gameName, tagLine);
            save(account);
        }
        return account;
    }

    @Override
    public Account find(String puuid) {
        Account account = accountRepository.findByPuuid(puuid).orElse(null);

        if (Objects.isNull(account)) {
            account = riotAccountService.findAccount(puuid);
            save(account);
        }
        return account;
    }

}
