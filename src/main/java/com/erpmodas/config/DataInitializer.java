package com.erpmodas.config;

import com.erpmodas.enums.TamanhoEnum;
import com.erpmodas.model.entidades.apoio.Tamanho;
import com.erpmodas.repository.apoio.TamanhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private TamanhoRepository tamanhoRepository;

    @Override
    public void run(ApplicationArguments args) {
        cadastrarTamanhos();
    }

    private void cadastrarTamanhos() {
        for (TamanhoEnum tamanho : TamanhoEnum.values()) {
            if(!tamanhoRepository.existsByTamanho(tamanho)) {
                Tamanho novo = new Tamanho();
                novo.setTamanho(tamanho);
                tamanhoRepository.save(novo);
            }
        }
    }
}
