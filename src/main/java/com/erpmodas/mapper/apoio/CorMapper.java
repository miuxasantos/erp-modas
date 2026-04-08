package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.model.entidades.apoio.Cor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CorMapper {
    CorDTO toDTO(Cor cor);

    Cor toEntity(CorDTO dto);

    java.util.List<CorDTO> toDTOList(java.util.List<Cor> lista);

    void updateEntityFromDTO(CorDTO dto, @MappingTarget Cor entity);
}
