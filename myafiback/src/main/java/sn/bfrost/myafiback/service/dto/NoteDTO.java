package sn.bfrost.myafiback.service.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private Long id;

    private String semestre;

    private String matiere;

    private int credit;

    private Float valeur;

    private UserDTO user;

}
