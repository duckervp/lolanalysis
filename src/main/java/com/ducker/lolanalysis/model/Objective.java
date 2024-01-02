package com.ducker.lolanalysis.model;

import com.ducker.lolanalysis.enums.ObjectiveType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
@Entity
@Table
@Builder
public class Objective {
    @Id
    @GeneratedValue
    private Long id;
    private Long teamId;
    @Enumerated(EnumType.STRING)
    private ObjectiveType type;
    private boolean first;
    private int kills;
}
