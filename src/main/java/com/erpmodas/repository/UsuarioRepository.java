package com.erpmodas.repository;

import com.erpmodas.enums.Cargo;
import com.erpmodas.model.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioById(Long usuarioId);
    Optional<Usuario> findUsuarioByEmail(String email);
    List<Usuario> findByCargo(Cargo cargo);
    List<Usuario> findByStatusTrue();
}
