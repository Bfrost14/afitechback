package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.CalendrierCours;
import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.domain.Salle;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.CalendrierCoursDTO;
import com.bfrost.universite.service.dto.CampusDTO;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.dto.FiliereDTO;
import com.bfrost.universite.service.dto.SalleDTO;
import com.bfrost.universite.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-18T16:20:55+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class CalendrierCoursMapperImpl implements CalendrierCoursMapper {

    @Override
    public CalendrierCours toEntity(CalendrierCoursDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CalendrierCours calendrierCours = new CalendrierCours();

        calendrierCours.setId( dto.getId() );
        calendrierCours.setDateDebut( dto.getDateDebut() );
        calendrierCours.setDateFin( dto.getDateFin() );
        calendrierCours.cours( coursDTOToCours( dto.getCours() ) );
        calendrierCours.salle( salleDTOToSalle( dto.getSalle() ) );
        calendrierCours.filiere( filiereDTOToFiliere( dto.getFiliere() ) );

        return calendrierCours;
    }

    @Override
    public List<CalendrierCours> toEntity(List<CalendrierCoursDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CalendrierCours> list = new ArrayList<CalendrierCours>( dtoList.size() );
        for ( CalendrierCoursDTO calendrierCoursDTO : dtoList ) {
            list.add( toEntity( calendrierCoursDTO ) );
        }

        return list;
    }

    @Override
    public List<CalendrierCoursDTO> toDto(List<CalendrierCours> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CalendrierCoursDTO> list = new ArrayList<CalendrierCoursDTO>( entityList.size() );
        for ( CalendrierCours calendrierCours : entityList ) {
            list.add( toDto( calendrierCours ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CalendrierCours entity, CalendrierCoursDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getDateDebut() != null ) {
            entity.setDateDebut( dto.getDateDebut() );
        }
        if ( dto.getDateFin() != null ) {
            entity.setDateFin( dto.getDateFin() );
        }
        if ( dto.getCours() != null ) {
            if ( entity.getCours() == null ) {
                entity.cours( new Cours() );
            }
            coursDTOToCours1( dto.getCours(), entity.getCours() );
        }
        if ( dto.getSalle() != null ) {
            if ( entity.getSalle() == null ) {
                entity.salle( new Salle() );
            }
            salleDTOToSalle1( dto.getSalle(), entity.getSalle() );
        }
        if ( dto.getFiliere() != null ) {
            if ( entity.getFiliere() == null ) {
                entity.filiere( new Filiere() );
            }
            filiereDTOToFiliere1( dto.getFiliere(), entity.getFiliere() );
        }
    }

    @Override
    public CalendrierCoursDTO toDto(CalendrierCours s) {
        if ( s == null ) {
            return null;
        }

        CalendrierCoursDTO calendrierCoursDTO = new CalendrierCoursDTO();

        calendrierCoursDTO.setCours( toDtoCoursId( s.getCours() ) );
        calendrierCoursDTO.setSalle( toDtoSalleId( s.getSalle() ) );
        calendrierCoursDTO.setFiliere( toDtoFiliereId( s.getFiliere() ) );
        calendrierCoursDTO.setId( s.getId() );
        calendrierCoursDTO.setDateDebut( s.getDateDebut() );
        calendrierCoursDTO.setDateFin( s.getDateFin() );

        return calendrierCoursDTO;
    }

    @Override
    public CoursDTO toDtoCoursId(Cours cours) {
        if ( cours == null ) {
            return null;
        }

        CoursDTO coursDTO = new CoursDTO();

        coursDTO.setId( cours.getId() );

        return coursDTO;
    }

    @Override
    public SalleDTO toDtoSalleId(Salle salle) {
        if ( salle == null ) {
            return null;
        }

        SalleDTO salleDTO = new SalleDTO();

        salleDTO.setId( salle.getId() );

        return salleDTO;
    }

    @Override
    public FiliereDTO toDtoFiliereId(Filiere filiere) {
        if ( filiere == null ) {
            return null;
        }

        FiliereDTO filiereDTO = new FiliereDTO();

        filiereDTO.setId( filiere.getId() );

        return filiereDTO;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setLogin( userDTO.getLogin() );

        return user;
    }

    protected Cours coursDTOToCours(CoursDTO coursDTO) {
        if ( coursDTO == null ) {
            return null;
        }

        Cours cours = new Cours();

        cours.setId( coursDTO.getId() );
        cours.setIntitule( coursDTO.getIntitule() );
        cours.professeur( userDTOToUser( coursDTO.getProfesseur() ) );

        return cours;
    }

    protected Campus campusDTOToCampus(CampusDTO campusDTO) {
        if ( campusDTO == null ) {
            return null;
        }

        Campus campus = new Campus();

        campus.setId( campusDTO.getId() );
        campus.setNom( campusDTO.getNom() );

        return campus;
    }

    protected Salle salleDTOToSalle(SalleDTO salleDTO) {
        if ( salleDTO == null ) {
            return null;
        }

        Salle salle = new Salle();

        salle.setId( salleDTO.getId() );
        salle.setNumero( salleDTO.getNumero() );
        salle.campus( campusDTOToCampus( salleDTO.getCampus() ) );

        return salle;
    }

    protected Filiere filiereDTOToFiliere(FiliereDTO filiereDTO) {
        if ( filiereDTO == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setId( filiereDTO.getId() );
        filiere.setNom( filiereDTO.getNom() );

        return filiere;
    }

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        mappingTarget.setId( userDTO.getId() );
        mappingTarget.setLogin( userDTO.getLogin() );
    }

    protected void coursDTOToCours1(CoursDTO coursDTO, Cours mappingTarget) {
        if ( coursDTO == null ) {
            return;
        }

        mappingTarget.setId( coursDTO.getId() );
        mappingTarget.setIntitule( coursDTO.getIntitule() );
        if ( coursDTO.getProfesseur() != null ) {
            if ( mappingTarget.getProfesseur() == null ) {
                mappingTarget.professeur( new User() );
            }
            userDTOToUser1( coursDTO.getProfesseur(), mappingTarget.getProfesseur() );
        }
        else {
            mappingTarget.professeur( null );
        }
    }

    protected void campusDTOToCampus1(CampusDTO campusDTO, Campus mappingTarget) {
        if ( campusDTO == null ) {
            return;
        }

        mappingTarget.setId( campusDTO.getId() );
        mappingTarget.setNom( campusDTO.getNom() );
    }

    protected void salleDTOToSalle1(SalleDTO salleDTO, Salle mappingTarget) {
        if ( salleDTO == null ) {
            return;
        }

        mappingTarget.setId( salleDTO.getId() );
        mappingTarget.setNumero( salleDTO.getNumero() );
        if ( salleDTO.getCampus() != null ) {
            if ( mappingTarget.getCampus() == null ) {
                mappingTarget.campus( new Campus() );
            }
            campusDTOToCampus1( salleDTO.getCampus(), mappingTarget.getCampus() );
        }
        else {
            mappingTarget.campus( null );
        }
    }

    protected void filiereDTOToFiliere1(FiliereDTO filiereDTO, Filiere mappingTarget) {
        if ( filiereDTO == null ) {
            return;
        }

        mappingTarget.setId( filiereDTO.getId() );
        mappingTarget.setNom( filiereDTO.getNom() );
    }
}
