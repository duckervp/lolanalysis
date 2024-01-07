package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByPuuid(String puuid);

    Optional<Account> findByGameNameAndTagLine(String gameName, String tagLine);
}
