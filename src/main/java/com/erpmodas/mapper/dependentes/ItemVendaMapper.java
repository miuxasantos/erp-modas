package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.dependentes.itemVenda.ItemVendaResponseDTO;
import com.erpmodas.mapper.apoio.VariacaoProdutoMapper;
import com.erpmodas.model.entidades.dependentes.ItemVenda;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VariacaoProdutoMapper.class})
public interface ItemVendaMapper {

    ItemVendaResponseDTO toDTO(ItemVenda entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    ItemVenda toEntity(ItemVendaResponseDTO dto);

    java.util.List<ItemVendaResponseDTO> toDTOList(java.util.List<ItemVenda> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variacaoProduto", ignore = true)
    void updateEntityFromDTO(ItemVendaResponseDTO dto, @MappingTarget ItemVenda entity);
}
