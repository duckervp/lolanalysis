package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.MatchLookup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchLookupRepository extends JpaRepository<MatchLookup, String> {
    List<MatchLookup> findByMatchIdIn(List<String> matchIds);

    @Query("SELECT m FROM MatchLookup m ORDER BY m.matchId DESC")
    List<MatchLookup> findMatchLookup(Pageable pageable);
}
