package com.ducker.lolanalysis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Perk {
    @Id
    private String id;
    private String participantId;
    private int defense;
    private int flex;
    private int offense;
    private int primaryStylePerk;
    private int subStylePerk;
}
