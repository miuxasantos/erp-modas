package com.erpmodas.repository.apoio;


import com.erpmodas.model.entidades.apoio.Tecido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TecidoRepository extends JpaRepository<Tecido, Long>{
    Optional<Tecido> findByNome(String nome);
    List<Tecido> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
