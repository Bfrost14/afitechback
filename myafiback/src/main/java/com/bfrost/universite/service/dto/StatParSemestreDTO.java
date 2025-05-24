package com.bfrost.universite.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatParSemestreDTO {
    private SemestreDTO semestre;
    private StatistiquesSemestreDTO stats;
}
