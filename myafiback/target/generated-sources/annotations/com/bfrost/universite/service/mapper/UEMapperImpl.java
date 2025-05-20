package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.UE;
import com.bfrost.universite.service.dto.UEDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T19:29:14+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UEMapperImpl implements UEMapper {

    @Override
    public UE toEntity(UEDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UE uE = new UE();

        uE.setId( dto.getId() );
        uE.setNom( dto.getNom() );

        return uE;
    }

    @Override
    public UEDTO toDto(UE entity) {
        if ( entity == null ) {
            return null;
        }

        UEDTO uEDTO = new UEDTO();

        uEDTO.setId( entity.getId() );
        uEDTO.setNom( entity.getNom() );

        return uEDTO;
    }

    @Override
    public List<UE> toEntity(List<UEDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UE> list = new ArrayList<UE>( dtoList.size() );
        for ( UEDTO uEDTO : dtoList ) {
            list.add( toEntity( uEDTO ) );
        }

        return list;
    }

    @Override
    public List<UEDTO> toDto(List<UE> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UEDTO> list = new ArrayList<UEDTO>( entityList.size() );
        for ( UE uE : entityList ) {
            list.add( toDto( uE ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(UE entity, UEDTO dto) {
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
