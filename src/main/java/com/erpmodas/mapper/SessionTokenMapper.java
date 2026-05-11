package com.erpmodas.mapper;

import com.erpmodas.dto.sessiontoken.SessionTokenDTO;
import com.erpmodas.model.entidades.SessionToken;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SessionTokenMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    SessionTokenDTO toDTO(SessionToken entity);

    @Mapping(target = "usuario", ignore = true)
    SessionToken toEntity(SessionTokenDTO dto);

    java.util.List<SessionTokenDTO> toDTOList(java.util.List<SessionToken> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(SessionTokenDTO dto, @MappingTarget SessionToken entity);
}
