package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoReqDTO;
import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoResponseDTO;
import com.erpmodas.dto.apoio.variacaoProdutoDto.VariacaoProdutoUpdateDTO;
import com.erpmodas.mapper.ProdutoMapper;
import com.erpmodas.model.entidades.apoio.VariacaoProduto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, CorMapper.class, TamanhoMapper.class})
public interface VariacaoProdutoMapper {

    VariacaoProdutoResponseDTO toDTO(VariacaoProduto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cor", ignore = true)
    @Mapping(target = "tamanho", ignore = true)
    VariacaoProduto toEntity(VariacaoProdutoReqDTO dto);

    java.util.List<VariacaoProdutoResponseDTO> toDTOList(java.util.List<VariacaoProduto> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "cor", ignore = true)
    @Mapping(target = "tamanho", ignore = true)
    @Mapping(target = "estoque", ignore = true)
    void updateEntityFromDTO(VariacaoProdutoUpdateDTO dto, @MappingTarget VariacaoProduto entity);
}
