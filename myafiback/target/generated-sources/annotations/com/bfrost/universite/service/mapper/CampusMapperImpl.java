package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.service.dto.CampusDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T17:00:10+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class CampusMapperImpl implements CampusMapper {

    @Override
    public Campus toEntity(CampusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Campus campus = new Campus();

        campus.setId( dto.getId() );
        campus.setNom( dto.getNom() );

        return campus;
    }

    @Override
    public CampusDTO toDto(Campus entity) {
        if ( entity == null ) {
            return null;
        }

        CampusDTO campusDTO = new CampusDTO();

        campusDTO.setId( entity.getId() );
        campusDTO.setNom( entity.getNom() );

        return campusDTO;
    }

    @Override
    public List<Campus> toEntity(List<CampusDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Campus> list = new ArrayList<Campus>( dtoList.size() );
        for ( CampusDTO campusDTO : dtoList ) {
            list.add( toEntity( campusDTO ) );
        }

        return list;
    }

    @Override
    public List<CampusDTO> toDto(List<Campus> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CampusDTO> list = new ArrayList<CampusDTO>( entityList.size() );
        for ( Campus campus : entityList ) {
            list.add( toDto( campus ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Campus entity, CampusDTO dto) {
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

    @Override
    public Set<Campus> toEntity(Set<CampusDTO> campus) {
        if ( campus == null ) {
            return null;
        }

        Set<Campus> set = new LinkedHashSet<Campus>( Math.max( (int) ( campus.size() / .75f ) + 1, 16 ) );
        for ( CampusDTO campusDTO : campus ) {
            set.add( toEntity( campusDTO ) );
        }

        return set;
    }
}
