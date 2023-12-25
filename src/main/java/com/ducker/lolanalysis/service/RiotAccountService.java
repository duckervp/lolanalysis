package com.ducker.lolanalysis.service;

import com.ducker.lolanalysis.model.Account;

public interface RiotAccountService {
    Account findAccount(String gameName, String tagLine);

    Account findAccount(String puuid);
}
