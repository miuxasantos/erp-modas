package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.TamanhoDTO;
import com.erpmodas.model.entidades.apoio.Tamanho;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TamanhoMapper {
    TamanhoDTO toDTO(Tamanho tamanho);

    Tamanho toEntity(TamanhoDTO dto);

    java.util.List<TamanhoDTO> toDTOList(java.util.List<Tamanho> lista);

    void updateEntityFromDTO(TamanhoDTO dto, @MappingTarget Tamanho entity);
}
