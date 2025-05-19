package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.domain.UE;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.dto.UEDTO;
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
public class MatiereMapperImpl implements MatiereMapper {

    @Override
    public Matiere toEntity(MatiereDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Matiere matiere = new Matiere();

        matiere.setId( dto.getId() );
        matiere.setNom( dto.getNom() );
        matiere.setCredit( dto.getCredit() );
        matiere.ue( uEDTOToUE( dto.getUe() ) );

        return matiere;
    }

    @Override
    public List<Matiere> toEntity(List<MatiereDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Matiere> list = new ArrayList<Matiere>( dtoList.size() );
        for ( MatiereDTO matiereDTO : dtoList ) {
            list.add( toEntity( matiereDTO ) );
        }

        return list;
    }

    @Override
    public List<MatiereDTO> toDto(List<Matiere> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MatiereDTO> list = new ArrayList<MatiereDTO>( entityList.size() );
        for ( Matiere matiere : entityList ) {
            list.add( toDto( matiere ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Matiere entity, MatiereDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNom() != null ) {
            entity.setNom( dto.getNom() );
        }
        if ( dto.getCredit() != null ) {
            entity.setCredit( dto.getCredit() );
        }
        if ( dto.getUe() != null ) {
            if ( entity.getUe() == null ) {
                entity.ue( new UE() );
            }
            uEDTOToUE1( dto.getUe(), entity.getUe() );
        }
    }

    @Override
    public MatiereDTO toDto(Matiere s) {
        if ( s == null ) {
            return null;
        }

        MatiereDTO matiereDTO = new MatiereDTO();

        matiereDTO.setUe( toDtoUEId( s.getUe() ) );
        matiereDTO.setId( s.getId() );
        matiereDTO.setNom( s.getNom() );
        matiereDTO.setCredit( s.getCredit() );

        return matiereDTO;
    }

    @Override
    public UEDTO toDtoUEId(UE uE) {
        if ( uE == null ) {
            return null;
        }

        UEDTO uEDTO = new UEDTO();

        uEDTO.setId( uE.getId() );

        return uEDTO;
    }

    protected UE uEDTOToUE(UEDTO uEDTO) {
        if ( uEDTO == null ) {
            return null;
        }

        UE uE = new UE();

        uE.setId( uEDTO.getId() );
        uE.setNom( uEDTO.getNom() );

        return uE;
    }

    protected void uEDTOToUE1(UEDTO uEDTO, UE mappingTarget) {
        if ( uEDTO == null ) {
            return;
        }

        mappingTarget.setId( uEDTO.getId() );
        mappingTarget.setNom( uEDTO.getNom() );
    }
}
