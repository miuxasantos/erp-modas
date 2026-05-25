package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.contasPagar.ContasPagarDTO;
import com.erpmodas.model.entidades.dependentes.ContasPagar;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContasPagarMapper {

    @Mapping(source = "compra.fornecedor.nome", target = "fornecedorNome")
    ContasPagarDTO toDTO(ContasPagar entity);

    ContasPagar toEntity(ContasPagarDTO dto);

    default java.util.List<ContasPagarDTO> toDTOList(java.util.List<ContasPagar> lista) {
        return lista.stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
    //java.util.List<ContasPagarDTO> toDTOList(java.util.List<ContasPagar> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ContasPagarDTO dto, @MappingTarget ContasPagar entity);
}
