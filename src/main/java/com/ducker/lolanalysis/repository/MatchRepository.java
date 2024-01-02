package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.Match;
import com.ducker.lolanalysis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
    Optional<Match> findByMatchId(String matchId);
}
