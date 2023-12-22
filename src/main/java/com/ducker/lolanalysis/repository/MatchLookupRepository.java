package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.MatchLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchLookupRepository extends JpaRepository<MatchLookup, String> {
    List<MatchLookup> findByMatchIn(List<String> matches);
}
