package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.TamanhoDTO;
import com.erpmodas.model.entidades.apoio.Tamanho;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TamanhoMapper {
    TamanhoDTO toDTO(Tamanho tamanho);

    @Mapping(target = "id", ignore = true)
    Tamanho toEntity(TamanhoDTO dto);

    java.util.List<TamanhoDTO> toDTOList(java.util.List<Tamanho> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TamanhoDTO dto, @MappingTarget Tamanho entity);
}
