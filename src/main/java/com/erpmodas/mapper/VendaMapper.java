package com.erpmodas.mapper;


import com.erpmodas.dto.venda.VendaReqDTO;
import com.erpmodas.dto.venda.VendaResponseDTO;
import com.erpmodas.dto.venda.VendaUpdateDTO;
import com.erpmodas.mapper.dependentes.ItemVendaMapper;
import com.erpmodas.model.entidades.Venda;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemVendaMapper.class})
public interface VendaMapper {

    VendaResponseDTO toDTO(Venda entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "itensVenda", ignore = true)
    @Mapping(target = "contasReceber", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    Venda toEntity(VendaReqDTO dto);

    java.util.List<VendaResponseDTO> toDTOList(java.util.List<Venda> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "formaPagamento", ignore = true)
    @Mapping(target = "numeroParcelas", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "itensVenda", ignore = true)
    @Mapping(target = "contasReceber", ignore = true)
    @Mapping(target = "dataVenda", ignore = true)
    void updateEntityFromDTO(VendaUpdateDTO dto, @MappingTarget Venda entity);
}
