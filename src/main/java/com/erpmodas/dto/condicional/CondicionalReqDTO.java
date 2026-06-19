package com.erpmodas.dto.condicional;

import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalReqDTO;
import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalResponseDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CondicionalReqDTO {
    @NotNull
    private Long clienteId;
    @NotNull
    private LocalDate dataInicio;
    private Integer periodo;
    private LocalDate dataFinal;
    @NotEmpty
    @NotNull
    private List<ItemCondicionalReqDTO> itensCondicional;
}
