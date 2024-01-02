package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.Ban;
import com.ducker.lolanalysis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {
}
