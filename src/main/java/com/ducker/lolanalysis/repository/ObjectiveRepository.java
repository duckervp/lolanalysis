package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
    List<Objective> findByTeamIdIn(List<Long> teamIds);
}
