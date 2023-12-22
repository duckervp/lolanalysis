package com.ducker.lolanalysis.model;

import com.ducker.lolanalysis.enums.ObjectiveType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Objective {
    @Id
    @GeneratedValue
    private Long id;
    private Long teamId;
    private ObjectiveType type;
    private boolean first;
    private int kills;
}
