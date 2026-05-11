package com.erpmodas.mapper;

import com.erpmodas.dto.compra.CompraDTO;
import com.erpmodas.dto.condicional.CondicionalDTO;
import com.erpmodas.model.entidades.Compra;
import com.erpmodas.model.entidades.Condicional;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CondicionalMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "itensCondicional", target = "itens")
    CondicionalDTO toDTO(Condicional entity);

    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "itensCondicional", ignore = true)
    Condicional toEntity(CondicionalDTO dto);

    java.util.List<CondicionalDTO> toDTOList(java.util.List<Condicional> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CondicionalDTO dto, @MappingTarget Condicional entity);
}
