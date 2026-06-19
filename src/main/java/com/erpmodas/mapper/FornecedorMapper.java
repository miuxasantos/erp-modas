package com.erpmodas.mapper;


import com.erpmodas.dto.fornecedor.FornecedorReqDTO;
import com.erpmodas.dto.fornecedor.FornecedorResponseDTO;
import com.erpmodas.dto.fornecedor.FornecedorUpdateDTO;
import com.erpmodas.model.entidades.Fornecedor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {
    FornecedorResponseDTO toDTO(Fornecedor fornecedor);

    @Mapping(target = "id", ignore = true)
    Fornecedor toEntity(FornecedorReqDTO dto);

    java.util.List<FornecedorResponseDTO> toDTOList(java.util.List<Fornecedor> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(FornecedorUpdateDTO dto, @MappingTarget Fornecedor entity);
}
