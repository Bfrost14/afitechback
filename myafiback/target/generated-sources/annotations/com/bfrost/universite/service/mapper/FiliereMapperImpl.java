package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.service.dto.FiliereDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T02:38:42+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class FiliereMapperImpl implements FiliereMapper {

    @Override
    public Filiere toEntity(FiliereDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setId( dto.getId() );
        filiere.setNom( dto.getNom() );

        return filiere;
    }

    @Override
    public FiliereDTO toDto(Filiere entity) {
        if ( entity == null ) {
            return null;
        }

        FiliereDTO filiereDTO = new FiliereDTO();

        filiereDTO.setId( entity.getId() );
        filiereDTO.setNom( entity.getNom() );

        return filiereDTO;
    }

    @Override
    public List<Filiere> toEntity(List<FiliereDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Filiere> list = new ArrayList<Filiere>( dtoList.size() );
        for ( FiliereDTO filiereDTO : dtoList ) {
            list.add( toEntity( filiereDTO ) );
        }

        return list;
    }

    @Override
    public List<FiliereDTO> toDto(List<Filiere> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FiliereDTO> list = new ArrayList<FiliereDTO>( entityList.size() );
        for ( Filiere filiere : entityList ) {
            list.add( toDto( filiere ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Filiere entity, FiliereDTO dto) {
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
