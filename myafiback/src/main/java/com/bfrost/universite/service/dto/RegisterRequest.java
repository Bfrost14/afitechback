package com.bfrost.universite.service.dto;

import com.bfrost.universite.domain.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String nom;
    private String email;
    private String password;
    private Role role;
    private String flotte;
    private long nbrChauffeur;
    private long telephone;

}
