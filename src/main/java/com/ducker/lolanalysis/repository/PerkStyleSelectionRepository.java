package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.PerkStyleSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerkStyleSelectionRepository extends JpaRepository<PerkStyleSelection, Long> {

    List<PerkStyleSelection> findByPerkIdIn(List<String> perkIds);
}
