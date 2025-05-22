package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.service.dto.AuthorityDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T02:38:41+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class AuthorityMapperImpl implements AuthorityMapper {

    @Override
    public Authority toEntity(AuthorityDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Authority authority = new Authority();

        authority.setName( dto.getName() );

        return authority;
    }

    @Override
    public AuthorityDTO toDto(Authority entity) {
        if ( entity == null ) {
            return null;
        }

        AuthorityDTO authorityDTO = new AuthorityDTO();

        authorityDTO.setName( entity.getName() );

        return authorityDTO;
    }

    @Override
    public List<Authority> toEntity(List<AuthorityDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Authority> list = new ArrayList<Authority>( dtoList.size() );
        for ( AuthorityDTO authorityDTO : dtoList ) {
            list.add( toEntity( authorityDTO ) );
        }

        return list;
    }

    @Override
    public List<AuthorityDTO> toDto(List<Authority> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AuthorityDTO> list = new ArrayList<AuthorityDTO>( entityList.size() );
        for ( Authority authority : entityList ) {
            list.add( toDto( authority ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Authority entity, AuthorityDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }

    @Override
    public Set<Authority> toEntity(Set<AuthorityDTO> authorities) {
        if ( authorities == null ) {
            return null;
        }

        Set<Authority> set = new LinkedHashSet<Authority>( Math.max( (int) ( authorities.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : authorities ) {
            set.add( toEntity( authorityDTO ) );
        }

        return set;
    }
}
