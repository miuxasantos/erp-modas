package com.erpmodas.repository;

import com.erpmodas.model.entidades.Assessoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessoriaRepository extends JpaRepository<Assessoria, Long> {
    Optional<Assessoria> findByNome(String nome);
    List<Assessoria> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
