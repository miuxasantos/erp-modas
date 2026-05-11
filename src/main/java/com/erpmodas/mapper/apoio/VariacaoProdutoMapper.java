package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.VariacaoProdutoDTO;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VariacaoProdutoMapper {

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "cor.id", target = "corId")
    @Mapping(source = "tamanho.id", target = "tamanhoId")
    VariacaoProdutoDTO toDTO(VariacaoProduto entity);

    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cor", ignore = true)
    @Mapping(target = "tamanho", ignore = true)
    VariacaoProduto toEntity(VariacaoProdutoDTO dto);

    java.util.List<VariacaoProdutoDTO> toDTOList(java.util.List<VariacaoProduto> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cor", ignore = true)
    @Mapping(target = "tamanho", ignore = true)
    void updateEntityFromDTO(VariacaoProdutoDTO dto, @MappingTarget VariacaoProduto entity);
}
