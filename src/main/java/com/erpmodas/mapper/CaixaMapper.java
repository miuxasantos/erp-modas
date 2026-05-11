package com.erpmodas.mapper;

import com.erpmodas.dto.caixa.CaixaDTO;
import com.erpmodas.mapper.dependentes.MovimentacoesCaixaMapper;
import com.erpmodas.model.entidades.Caixa;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = MovimentacoesCaixaMapper.class)
public interface CaixaMapper {

    @Mapping(target = "totalEntradas", expression = "java(entity.getTotalEntradas())")
    @Mapping(target = "totalSaidas", expression = "java(entity.getTotalSaidas())")
    @Mapping(target = "saldoTotal", expression = "java(entity.getSaldoTotal())")
    CaixaDTO toDTO(Caixa entity);

    Caixa toEntity(CaixaDTO dto);

    java.util.List<CaixaDTO> toDTOList(java.util.List<Caixa> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CaixaDTO dto, @MappingTarget Caixa entity);
}
