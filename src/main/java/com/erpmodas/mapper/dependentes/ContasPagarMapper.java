package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContasPagarMapper {

    @Mapping(source = "compra.id", target = "compraId")
    ContasPagarDTO toDTO(ContasPagar entity);

    @Mapping(target = "compra", ignore = true)
    ContasPagar toEntity(ContasPagarDTO dto);

    java.util.List<ContasPagarDTO> toDTOList(java.util.List<ContasPagar> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ContasPagarDTO dto, @MappingTarget ContasPagar entity);
}
