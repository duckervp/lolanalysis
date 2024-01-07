package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.model.Account;

public interface AccountService {
    void save(Account account);

    Account find(String gameName, String tagLine);

    Account find(String puuid);
}
