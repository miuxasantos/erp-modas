package com.erpmodas.dto.condicional;

import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CondicionalUpdateDTO {
    private Long clienteId;
    private LocalDate dataInicio;
    private Integer periodo;
    private LocalDate dataFinal;
    private List<ItemCondicionalResponseDTO> itensCondicional;
}
