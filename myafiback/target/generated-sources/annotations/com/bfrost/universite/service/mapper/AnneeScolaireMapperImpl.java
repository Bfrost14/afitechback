package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.AnneeScolaire;
import com.bfrost.universite.service.dto.AnneeScolaireDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-23T20:34:29+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class AnneeScolaireMapperImpl implements AnneeScolaireMapper {

    @Override
    public AnneeScolaire toEntity(AnneeScolaireDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AnneeScolaire anneeScolaire = new AnneeScolaire();

        anneeScolaire.setId( dto.getId() );
        anneeScolaire.setNom( dto.getNom() );

        return anneeScolaire;
    }

    @Override
    public AnneeScolaireDTO toDto(AnneeScolaire entity) {
        if ( entity == null ) {
            return null;
        }

        AnneeScolaireDTO anneeScolaireDTO = new AnneeScolaireDTO();

        anneeScolaireDTO.setId( entity.getId() );
        anneeScolaireDTO.setNom( entity.getNom() );

        return anneeScolaireDTO;
    }

    @Override
    public List<AnneeScolaire> toEntity(List<AnneeScolaireDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<AnneeScolaire> list = new ArrayList<AnneeScolaire>( dtoList.size() );
        for ( AnneeScolaireDTO anneeScolaireDTO : dtoList ) {
            list.add( toEntity( anneeScolaireDTO ) );
        }

        return list;
    }

    @Override
    public List<AnneeScolaireDTO> toDto(List<AnneeScolaire> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AnneeScolaireDTO> list = new ArrayList<AnneeScolaireDTO>( entityList.size() );
        for ( AnneeScolaire anneeScolaire : entityList ) {
            list.add( toDto( anneeScolaire ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(AnneeScolaire entity, AnneeScolaireDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNom() != null ) {
            entity.setNom( dto.getNom() );
        }
    }
}
