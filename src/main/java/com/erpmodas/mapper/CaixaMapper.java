package com.erpmodas.mapper;

import com.erpmodas.dto.caixa.CaixaResponseDTO;
import com.erpmodas.mapper.dependentes.MovimentacoesCaixaMapper;
import com.erpmodas.model.entidades.Caixa;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MovimentacoesCaixaMapper.class})
public interface CaixaMapper {

    @Mapping(target = "totalEntradas", expression = "java(entity.getTotalEntradas())")
    @Mapping(target = "totalSaidas", expression = "java(entity.getTotalSaidas())")
    @Mapping(target = "saldoTotal", expression = "java(entity.getSaldoTotal())")
    CaixaResponseDTO toDTO(Caixa entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movimentacoesCaixa", ignore = true)
    Caixa toEntity(CaixaResponseDTO dto);

    java.util.List<CaixaResponseDTO> toDTOList(java.util.List<Caixa> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movimentacoesCaixa", ignore = true)
    void updateEntityFromDTO(CaixaResponseDTO dto, @MappingTarget Caixa entity);
}
