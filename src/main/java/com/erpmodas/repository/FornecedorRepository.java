package com.erpmodas.repository;

import com.erpmodas.model.entidades.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findFornecedorById(Long fornecedorId);
    Optional<Fornecedor> findFornecedorByNome(String nome);
    List<Fornecedor> findByAssessoria(String assessoria);
    Optional<Fornecedor> findByContato(String contato);
}
