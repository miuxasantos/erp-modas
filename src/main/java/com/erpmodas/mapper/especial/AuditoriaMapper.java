package com.erpmodas.mapper.especial;

import com.erpmodas.dto.especial.AuditoriaDTO;
import com.erpmodas.model.entidades.especial.Auditoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AuditoriaMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    AuditoriaDTO toDTO(Auditoria entity);

    @Mapping(target = "usuario", ignore = true)
    Auditoria toEntity(AuditoriaDTO dto);

    java.util.List<AuditoriaDTO> toDTOList(java.util.List<Auditoria> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(AuditoriaDTO dto, @MappingTarget Auditoria entity);
}
