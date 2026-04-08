package com.erpmodas.repository.apoio;

import com.erpmodas.enums.TamanhoEnum;
import com.erpmodas.model.entidades.apoio.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TamanhoRepository extends JpaRepository<Tamanho, Long> {

    Optional<Tamanho> findByTamanho(TamanhoEnum tamanho);

    List<Tamanho> findAllByOrderByTamanhoAsc();
}
