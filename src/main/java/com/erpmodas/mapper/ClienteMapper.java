package com.erpmodas.mapper;

import com.erpmodas.dto.cliente.ClienteDTO;
import com.erpmodas.model.entidades.Cliente;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "string")
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente entity);

    Cliente toEntity(ClienteDTO dto);

    java.util.List<ClienteDTO> toDTOList(java.util.List<Cliente> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ClienteDTO dto, @MappingTarget Cliente entity);
}
