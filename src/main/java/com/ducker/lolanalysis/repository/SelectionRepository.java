package com.ducker.lolanalysis.repository;

import com.ducker.lolanalysis.model.PerkStyleSelection;
import com.ducker.lolanalysis.model.Selection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, Long> {
    Optional<PerkStyleSelection> findByPerkAndAndVar1AndVar2AndVar3(int perk, int var1, int var2, int var3);

    List<Selection> findByIdIn(List<String> selectionIds);
}
