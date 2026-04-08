package com.erpmodas.repository;

import com.erpmodas.model.entidades.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findFornecedorById(Long fornecedorId);
    List<Fornecedor> findFornecedorByNome(String nome);
    List<Fornecedor> findByAssessoria(String assessoria);
    List<Fornecedor> findByContato(String contato);
}
