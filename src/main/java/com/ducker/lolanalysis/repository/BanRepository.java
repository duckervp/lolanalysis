package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.Ban;
import com.ducker.lolanalysis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {
    List<Ban> findByTeamIdIn(List<Long> teamIds);
}
