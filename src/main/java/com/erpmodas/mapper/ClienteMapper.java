package com.erpmodas.mapper;

import com.erpmodas.dto.cliente.ClienteResponseDTO;
import com.erpmodas.model.entidades.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponseDTO toDTO(Cliente entity);

    @Mapping(target = "id", ignore = true)
    Cliente toEntity(ClienteResponseDTO dto);

    java.util.List<ClienteResponseDTO> toDTOList(java.util.List<Cliente> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ClienteResponseDTO dto, @MappingTarget Cliente entity);
}
