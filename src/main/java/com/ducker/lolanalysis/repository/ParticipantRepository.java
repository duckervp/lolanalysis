package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {

    List<Participant> findByMatchId(String matchId);
}
