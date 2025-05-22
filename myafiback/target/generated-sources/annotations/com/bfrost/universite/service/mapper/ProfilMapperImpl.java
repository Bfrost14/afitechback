package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.domain.Profil;
import com.bfrost.universite.service.dto.AuthorityDTO;
import com.bfrost.universite.service.dto.ProfilDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T02:38:37+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class ProfilMapperImpl implements ProfilMapper {

    @Override
    public Profil toEntity(ProfilDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Profil profil = new Profil();

        profil.setId( dto.getId() );
        profil.setNom( dto.getNom() );
        profil.setAuthorities( authorityDTOSetToAuthoritySet( dto.getAuthorities() ) );

        return profil;
    }

    @Override
    public ProfilDTO toDto(Profil entity) {
        if ( entity == null ) {
            return null;
        }

        ProfilDTO profilDTO = new ProfilDTO();

        profilDTO.setId( entity.getId() );
        profilDTO.setNom( entity.getNom() );
        profilDTO.setAuthorities( authoritySetToAuthorityDTOSet( entity.getAuthorities() ) );

        return profilDTO;
    }

    @Override
    public List<Profil> toEntity(List<ProfilDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Profil> list = new ArrayList<Profil>( dtoList.size() );
        for ( ProfilDTO profilDTO : dtoList ) {
            list.add( toEntity( profilDTO ) );
        }

        return list;
    }

    @Override
    public List<ProfilDTO> toDto(List<Profil> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProfilDTO> list = new ArrayList<ProfilDTO>( entityList.size() );
        for ( Profil profil : entityList ) {
            list.add( toDto( profil ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Profil entity, ProfilDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNom() != null ) {
            entity.setNom( dto.getNom() );
        }
        if ( entity.getAuthorities() != null ) {
            Set<Authority> set = authorityDTOSetToAuthoritySet( dto.getAuthorities() );
            if ( set != null ) {
                entity.getAuthorities().clear();
                entity.getAuthorities().addAll( set );
            }
        }
        else {
            Set<Authority> set = authorityDTOSetToAuthoritySet( dto.getAuthorities() );
            if ( set != null ) {
                entity.setAuthorities( set );
            }
        }
    }

    protected Authority authorityDTOToAuthority(AuthorityDTO authorityDTO) {
        if ( authorityDTO == null ) {
            return null;
        }

        Authority authority = new Authority();

        authority.setName( authorityDTO.getName() );

        return authority;
    }

    protected Set<Authority> authorityDTOSetToAuthoritySet(Set<AuthorityDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Authority> set1 = new LinkedHashSet<Authority>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : set ) {
            set1.add( authorityDTOToAuthority( authorityDTO ) );
        }

        return set1;
    }

    protected AuthorityDTO authorityToAuthorityDTO(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityDTO authorityDTO = new AuthorityDTO();

        authorityDTO.setName( authority.getName() );

        return authorityDTO;
    }

    protected Set<AuthorityDTO> authoritySetToAuthorityDTOSet(Set<Authority> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorityDTO> set1 = new LinkedHashSet<AuthorityDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Authority authority : set ) {
            set1.add( authorityToAuthorityDTO( authority ) );
        }

        return set1;
    }
}
