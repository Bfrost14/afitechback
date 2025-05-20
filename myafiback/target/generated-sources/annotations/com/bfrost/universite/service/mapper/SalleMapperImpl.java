package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.domain.Salle;
import com.bfrost.universite.service.dto.CampusDTO;
import com.bfrost.universite.service.dto.SalleDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T19:29:13+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class SalleMapperImpl implements SalleMapper {

    @Override
    public Salle toEntity(SalleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Salle salle = new Salle();

        salle.setId( dto.getId() );
        salle.setNumero( dto.getNumero() );
        salle.campus( campusDTOToCampus( dto.getCampus() ) );

        return salle;
    }

    @Override
    public SalleDTO toDto(Salle entity) {
        if ( entity == null ) {
            return null;
        }

        SalleDTO salleDTO = new SalleDTO();

        salleDTO.setId( entity.getId() );
        salleDTO.setNumero( entity.getNumero() );
        salleDTO.setCampus( campusToCampusDTO( entity.getCampus() ) );

        return salleDTO;
    }

    @Override
    public List<Salle> toEntity(List<SalleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Salle> list = new ArrayList<Salle>( dtoList.size() );
        for ( SalleDTO salleDTO : dtoList ) {
            list.add( toEntity( salleDTO ) );
        }

        return list;
    }

    @Override
    public List<SalleDTO> toDto(List<Salle> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SalleDTO> list = new ArrayList<SalleDTO>( entityList.size() );
        for ( Salle salle : entityList ) {
            list.add( toDto( salle ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Salle entity, SalleDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNumero() != null ) {
            entity.setNumero( dto.getNumero() );
        }
        if ( dto.getCampus() != null ) {
            if ( entity.getCampus() == null ) {
                entity.campus( new Campus() );
            }
            campusDTOToCampus1( dto.getCampus(), entity.getCampus() );
        }
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

    protected CampusDTO campusToCampusDTO(Campus campus) {
        if ( campus == null ) {
            return null;
        }

        CampusDTO campusDTO = new CampusDTO();

        campusDTO.setId( campus.getId() );
        campusDTO.setNom( campus.getNom() );

        return campusDTO;
    }

    protected void campusDTOToCampus1(CampusDTO campusDTO, Campus mappingTarget) {
        if ( campusDTO == null ) {
            return;
        }

        mappingTarget.setId( campusDTO.getId() );
        mappingTarget.setNom( campusDTO.getNom() );
    }
}
