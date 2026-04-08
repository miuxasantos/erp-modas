package com.erpmodas.repository;

import com.erpmodas.model.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findClienteById(Long clienteId);
    Optional<Cliente> findClienteByDocumento(String documento);
    List<Cliente> findClienteByNome(String nome);
    List<Cliente> findClienteByContato(String contato);
}
