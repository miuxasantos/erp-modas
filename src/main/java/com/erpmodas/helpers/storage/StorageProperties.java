package com.erpmodas.helpers.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    private String dir;
    private DataSize tamanhoMax = DataSize.ofMegabytes(10);
    private Integer larguraFull = 1200;
    private Integer larguraThumb = 300;
    private Float qualidade = 0.85f;
}
