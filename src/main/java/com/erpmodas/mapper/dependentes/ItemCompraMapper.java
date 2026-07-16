package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.itemCompra.ItemCompraReqDTO;
import com.erpmodas.dto.dependentes.itemCompra.ItemCompraResponseDTO;
import com.erpmodas.mapper.apoio.VariacaoProdutoMapper;
import com.erpmodas.model.entidades.dependentes.ItemCompra;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VariacaoProdutoMapper.class})
public interface ItemCompraMapper {

    ItemCompraResponseDTO toDTO(ItemCompra entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    ItemCompra toEntity(ItemCompraResponseDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    ItemCompra toEntity(ItemCompraReqDTO dto);

    java.util.List<ItemCompraResponseDTO> toDTOList(java.util.List<ItemCompra> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    void updateEntityFromDTO(ItemCompraResponseDTO dto, @MappingTarget ItemCompra entity);
}
