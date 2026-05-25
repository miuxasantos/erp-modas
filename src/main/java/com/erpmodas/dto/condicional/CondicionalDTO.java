package com.erpmodas.dto.condicional;

import com.erpmodas.dto.cliente.ClienteDTO;
import com.erpmodas.dto.dependentes.itemCondicional.ItemCondicionalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CondicionalDTO {
    private Long id;
    private ClienteDTO cliente;
    private Long clienteId;
    private LocalDate dataInicio;
    private Integer periodo;
    private LocalDate dataFinal;
    private List<ItemCondicionalDTO> itensCondicional;
}