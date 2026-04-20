package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.TamanhoDTO;
import com.erpmodas.model.entidades.apoio.Tamanho;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TamanhoMapper {
    TamanhoDTO toDTO(Tamanho tamanho);

    Tamanho toEntity(TamanhoDTO dto);

    java.util.List<TamanhoDTO> toDTOList(java.util.List<Tamanho> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(TamanhoDTO dto, @MappingTarget Tamanho entity);
}
