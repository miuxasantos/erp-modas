package com.erpmodas.mapper;

import com.erpmodas.dto.condicional.CondicionalReqDTO;
import com.erpmodas.dto.condicional.CondicionalResponseDTO;
import com.erpmodas.dto.condicional.CondicionalUpdateDTO;
import com.erpmodas.mapper.dependentes.ItemCondicionalMapper;
import com.erpmodas.model.entidades.Condicional;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemCondicionalMapper.class})
public interface CondicionalMapper {

    CondicionalResponseDTO toDTO(Condicional entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "itensCondicional", ignore = true)
    Condicional toEntity(CondicionalReqDTO dto);

    java.util.List<CondicionalResponseDTO> toDTOList(java.util.List<Condicional> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "itensCondicional", ignore = true)
    void updateEntityFromDTO(CondicionalUpdateDTO dto, @MappingTarget Condicional entity);
}
