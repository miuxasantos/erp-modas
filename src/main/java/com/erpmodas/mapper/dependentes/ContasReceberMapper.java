package com.erpmodas.mapper.dependentes;


import com.erpmodas.dto.dependentes.contasReceber.ContasReceberDTO;
import com.erpmodas.model.entidades.dependentes.ContasReceber;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContasReceberMapper {

    @Mapping(source = "venda.cliente.nome", target = "clienteNome")
    ContasReceberDTO toDTO(ContasReceber entity);

    ContasReceber toEntity(ContasReceberDTO dto);

    java.util.List<ContasReceberDTO> toDTOList(java.util.List<ContasReceber> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ContasReceberDTO dto, @MappingTarget ContasReceber entity);
}
