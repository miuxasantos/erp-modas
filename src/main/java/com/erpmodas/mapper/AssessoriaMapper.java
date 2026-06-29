package com.erpmodas.mapper;

import com.erpmodas.dto.assessoria.AssessoriaDTO;
import com.erpmodas.model.entidades.Assessoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AssessoriaMapper {
    AssessoriaDTO toDTO(Assessoria assessoria);

    @Mapping(target = "id", ignore = true)
    Assessoria toEntity(AssessoriaDTO assessoriaDTO);

    java.util.List<AssessoriaDTO> toDTOList(java.util.List<Assessoria> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(AssessoriaDTO dto, @MappingTarget Assessoria entity);
}
