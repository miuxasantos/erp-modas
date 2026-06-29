package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.CorDTO;
import com.erpmodas.model.entidades.apoio.Cor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CorMapper {
    CorDTO toDTO(Cor cor);

    @Mapping(target = "id", ignore = true)
    Cor toEntity(CorDTO dto);

    java.util.List<CorDTO> toDTOList(java.util.List<Cor> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CorDTO dto, @MappingTarget Cor entity);
}
