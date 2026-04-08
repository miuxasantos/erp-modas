package com.erpmodas.repository;

import com.erpmodas.model.entidades.Condicional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CondicionalRepository extends JpaRepository<Condicional, Long> {
    List<Condicional> findByClienteId(Long clienteId);
    List<Condicional> findByDataInicio(LocalDate dataInicio);
    List<Condicional> findByPeriodo(Integer periodo);
}
