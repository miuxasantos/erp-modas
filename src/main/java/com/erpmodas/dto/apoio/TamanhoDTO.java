package com.erpmodas.dto.apoio;

import com.erpmodas.enums.TamanhoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TamanhoDTO {
    private Long id;
    private TamanhoEnum tamanho;
}
