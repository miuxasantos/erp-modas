package com.erpmodas.repository;

import com.erpmodas.model.entidades.Condicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CondicionalRepository extends JpaRepository<Condicional, Long> {
    List<Condicional> findByClienteId(Long clienteId);
    List<Condicional> findByDataInicio(LocalDate dataInicio);
    List<Condicional> findByPeriodo(Integer periodo);
    Optional<Condicional> findCondicionalById(Long aLong);

    @Query("SELECT c FROM Condicional c LEFT JOIN FETCH c.itensCondicional WHERE c.id = :id")
    Optional<Condicional> findByIdWithItens(@Param("id") Long id);
}
