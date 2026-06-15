package com.erpmodas.mapper;

import com.erpmodas.dto.usuario.UsuarioResponseDTO;
import com.erpmodas.model.entidades.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDTO toDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioResponseDTO dto);

    java.util.List<UsuarioResponseDTO> toDTOList(java.util.List<Usuario> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UsuarioResponseDTO dto, @MappingTarget Usuario entity);
}
