package com.erpmodas.mapper;

import com.erpmodas.dto.cliente.ClienteReqDTO;
import com.erpmodas.dto.cliente.ClienteResponseDTO;
import com.erpmodas.dto.cliente.ClienteUpdateDTO;
import com.erpmodas.model.entidades.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponseDTO toDTO(Cliente entity);

    @Mapping(target = "id", ignore = true)
    Cliente toEntity(ClienteReqDTO dto);

    java.util.List<ClienteResponseDTO> toDTOList(java.util.List<Cliente> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ClienteUpdateDTO dto, @MappingTarget Cliente entity);
}
