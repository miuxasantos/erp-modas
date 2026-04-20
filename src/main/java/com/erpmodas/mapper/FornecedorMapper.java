package com.erpmodas.mapper;


import com.erpmodas.dto.fornecedor.FornecedorDTO;
import com.erpmodas.model.entidades.Fornecedor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "string")
public interface FornecedorMapper {
    FornecedorDTO toDTO(Fornecedor fornecedor);

    Fornecedor toEntity(FornecedorDTO dto);

    java.util.List<FornecedorDTO> toDTOList(java.util.List<Fornecedor> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(FornecedorDTO dto, @MappingTarget Fornecedor entity);
}
