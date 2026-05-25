package com.erpmodas.mapper;


import com.erpmodas.dto.venda.VendaDTO;
import com.erpmodas.model.entidades.Venda;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VendaMapper {

    VendaDTO toDTO(Venda entity);

    Venda toEntity(VendaDTO dto);

    java.util.List<VendaDTO> toDTOList(java.util.List<Venda> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(VendaDTO dto, @MappingTarget Venda entity);
}
