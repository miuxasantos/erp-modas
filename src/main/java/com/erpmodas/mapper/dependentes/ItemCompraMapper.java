package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.itemCompra.ItemCompraDTO;
import com.erpmodas.model.entidades.dependentes.ItemCompra;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ItemCompraMapper {

    @Mapping(source = "compra.id", target = "compraId")
    @Mapping(source = "variacaoProduto.id", target = "variacaoProdutoId")
    ItemCompraDTO toDTO(ItemCompra entity);

    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    ItemCompra toEntity(ItemCompraDTO dto);

    java.util.List<ItemCompraDTO> toDTOList(java.util.List<ItemCompra> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ItemCompraDTO dto, @MappingTarget ItemCompra entity);
}
