package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.Perk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerkRepository extends JpaRepository<Perk, Long> {

    List<Perk> findByParticipantIdIn(List<String> participantIds);
}
