package com.erpmodas.mapper;

import com.erpmodas.dto.categoria.CategoriaDTO;
import com.erpmodas.model.entidades.Categoria;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDTO toDTO(Categoria entity);

    Categoria toEntity(CategoriaDTO dto);

    java.util.List<CategoriaDTO> toDTOList(java.util.List<Categoria> lista);

    void updateEntityFromDTO(CategoriaDTO dto, @MappingTarget Categoria entity);
}
