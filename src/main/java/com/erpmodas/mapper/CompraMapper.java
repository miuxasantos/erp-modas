package com.erpmodas.mapper;

import com.erpmodas.dto.compra.CompraDTO;
import com.erpmodas.model.entidades.Compra;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompraMapper {

    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    @Mapping(source = "itensCompra", target = "itens")
    CompraDTO toDTO(Compra entity);

    @Mapping(target = "fornecedor", ignore = true)
    @Mapping(target = "itensCompra", ignore = true)
    @Mapping(target = "contasPagar", ignore = true)
    Compra toEntity(CompraDTO dto);

    java.util.List<CompraDTO> toDTOList(java.util.List<Compra> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CompraDTO dto, @MappingTarget Compra entity);
}
