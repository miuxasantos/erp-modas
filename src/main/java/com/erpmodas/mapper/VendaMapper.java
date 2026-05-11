package com.erpmodas.mapper;


import com.erpmodas.dto.venda.VendaDTO;
import com.erpmodas.model.entidades.Venda;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VendaMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "itensVenda", target = "itens")
    VendaDTO toDTO(Venda entity);

    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "itensVenda", ignore = true)
    @Mapping(target = "contasReceber", ignore = true)
    Venda toEntity(VendaDTO dto);

    java.util.List<VendaDTO> toDTOList(java.util.List<Venda> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(VendaDTO dto, @MappingTarget Venda entity);
}
