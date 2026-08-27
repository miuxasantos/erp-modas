package com.erpmodas.controller;

import com.erpmodas.dto.catalogo.CatalogoResponseDTO;
import com.erpmodas.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping
    public ResponseEntity<Page<CatalogoResponseDTO>> listar(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long corId,
            @RequestParam(required = false) Long tamanhoId,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(catalogoService.listarPublico(q, categoriaId, corId, tamanhoId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(catalogoService.buscarPorId(id));
    }
}
