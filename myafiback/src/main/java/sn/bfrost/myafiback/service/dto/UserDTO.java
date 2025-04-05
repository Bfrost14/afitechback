package sn.bfrost.myafiback.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.bfrost.myafiback.models.Role;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserDTO  {
    private Long id;

    private String nom;

    private String prenom;

    private String matricule;

    private String email;

    private String password;

    private Role role;

    private LocalDate dateDeNaissance;

    private String telephone;

    private String filiere;

    public String getUsername() {
        return email;
    }


}
