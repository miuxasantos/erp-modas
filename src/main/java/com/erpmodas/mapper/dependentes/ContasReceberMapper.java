package com.erpmodas.mapper.dependentes;


import com.erpmodas.dto.dependentes.contasReceber.ContasReceberDTO;
import com.erpmodas.model.entidades.dependentes.ContasReceber;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContasReceberMapper {

    @Mapping(source = "venda.id", target = "vendaId")
    ContasReceberDTO toDTO(ContasReceber entity);

    @Mapping(target = "venda", ignore = true)
    ContasReceber toEntity(ContasReceberDTO dto);

    java.util.List<ContasReceberDTO> toDTOList(java.util.List<ContasReceber> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ContasReceberDTO dto, @MappingTarget ContasReceber entity);
}
