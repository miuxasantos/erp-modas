package com.erpmodas.mapper;

import com.erpmodas.dto.compra.CompraResponseDTO;
import com.erpmodas.mapper.dependentes.ItemCompraMapper;
import com.erpmodas.model.entidades.Compra;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {FornecedorMapper.class, ItemCompraMapper.class})
public interface CompraMapper {

    CompraResponseDTO toDTO(Compra entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fornecedor", ignore = true)
    @Mapping(target = "itensCompra", ignore = true)
    @Mapping(target = "contasPagar", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    Compra toEntity(CompraResponseDTO dto);

    java.util.List<CompraResponseDTO> toDTOList(java.util.List<Compra> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fornecedor", ignore = true)
    @Mapping(target = "itensCompra", ignore = true)
    @Mapping(target = "contasPagar", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    void updateEntityFromDTO(CompraResponseDTO dto, @MappingTarget Compra entity);
}
