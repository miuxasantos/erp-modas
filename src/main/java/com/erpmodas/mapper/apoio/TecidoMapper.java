package com.erpmodas.mapper.apoio;

import com.erpmodas.dto.apoio.TecidoDTO;
import com.erpmodas.model.entidades.apoio.Tecido;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TecidoMapper {
    TecidoDTO toDTO(Tecido tecido);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caimento", ignore = true)
    @Mapping(target = "elasticidade", ignore = true)
    Tecido toEntity(TecidoDTO tecidoDTO);

    java.util.List<TecidoDTO> toDTOList(java.util.List<Tecido> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caimento", ignore = true)
    @Mapping(target = "elasticidade", ignore = true)
    void updateEntityFromDTO(TecidoDTO dto, @MappingTarget Tecido entity);
}
