package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Semestre;
import com.bfrost.universite.service.dto.SemestreDTO;
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
public class SemestreMapperImpl implements SemestreMapper {

    @Override
    public Semestre toEntity(SemestreDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Semestre semestre = new Semestre();

        semestre.setId( dto.getId() );
        semestre.setNom( dto.getNom() );

        return semestre;
    }

    @Override
    public SemestreDTO toDto(Semestre entity) {
        if ( entity == null ) {
            return null;
        }

        SemestreDTO semestreDTO = new SemestreDTO();

        semestreDTO.setId( entity.getId() );
        semestreDTO.setNom( entity.getNom() );

        return semestreDTO;
    }

    @Override
    public List<Semestre> toEntity(List<SemestreDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Semestre> list = new ArrayList<Semestre>( dtoList.size() );
        for ( SemestreDTO semestreDTO : dtoList ) {
            list.add( toEntity( semestreDTO ) );
        }

        return list;
    }

    @Override
    public List<SemestreDTO> toDto(List<Semestre> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SemestreDTO> list = new ArrayList<SemestreDTO>( entityList.size() );
        for ( Semestre semestre : entityList ) {
            list.add( toDto( semestre ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Semestre entity, SemestreDTO dto) {
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
