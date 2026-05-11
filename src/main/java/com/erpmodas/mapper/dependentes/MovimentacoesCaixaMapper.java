package com.erpmodas.mapper.dependentes;

import com.erpmodas.dto.categoria.CategoriaDTO;
import com.erpmodas.dto.dependentes.movimentacoesCaixa.MovimentacoesCaixaDTO;
import com.erpmodas.model.entidades.dependentes.MovimentacoesCaixa;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MovimentacoesCaixaMapper {

    @Mapping(source = "caixa.id", target = "caixaId")
    MovimentacoesCaixaDTO toDTO(MovimentacoesCaixa entity);

    @Mapping(target = "caixa", ignore = true)
    MovimentacoesCaixa toEntity(MovimentacoesCaixaDTO dto);

    java.util.List<MovimentacoesCaixaDTO> toDTOList(java.util.List<MovimentacoesCaixa> lista);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(MovimentacoesCaixaDTO dto, @MappingTarget MovimentacoesCaixa entity);
}
