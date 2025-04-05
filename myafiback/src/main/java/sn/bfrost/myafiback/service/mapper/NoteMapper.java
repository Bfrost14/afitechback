package sn.bfrost.myafiback.service.mapper;

import org.mapstruct.Mapper;
import sn.bfrost.myafiback.models.Note;
import sn.bfrost.myafiback.service.dto.NoteDTO;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {

}
