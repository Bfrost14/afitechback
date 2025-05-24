package com.bfrost.universite.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatistiquesSemestreDTO implements Serializable {
    private Double moyenne;
    private Double tauxPresence;
    private Double trendMoyenne;
    private Double trendPresence;
    private String iconMoyenne;
    private String iconPresence;
}
