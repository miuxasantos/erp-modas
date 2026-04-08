package com.erpmodas.mapper;

import com.erpmodas.dto.usuario.UsuarioDTO;
import com.erpmodas.model.entidades.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(UsuarioDTO dto);

    java.util.List<UsuarioDTO> toDTOList(java.util.List<UsuarioDTO> lista);

    void updateEntityFromDTO(UsuarioDTO dto, @MappingTarget Usuario entity);
}
