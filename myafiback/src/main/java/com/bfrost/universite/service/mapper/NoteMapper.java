package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.domain.Note;
import com.bfrost.universite.domain.Semestre;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.dto.NoteDTO;
import com.bfrost.universite.service.dto.SemestreDTO;
import com.bfrost.universite.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "matiere", source = "matiere", qualifiedByName = "matiereId")
    @Mapping(target = "semestre", source = "semestre", qualifiedByName = "semestreId")
    NoteDTO toDto(Note s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("matiereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatiereDTO toDtoMatiereId(Matiere matiere);

    @Named("semestreId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SemestreDTO toDtoSemestreId(Semestre semestre);
}
