package com.erpmodas.repository;

import com.erpmodas.model.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findClienteById(Long clienteId);
    Optional<Cliente> findClienteByDocumento(String documento);
    Optional<Cliente> findClienteByNome(String nome);
    Optional<Cliente> findClienteByContato(String contato);
}
