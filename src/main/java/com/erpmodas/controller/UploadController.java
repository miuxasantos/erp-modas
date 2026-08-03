package com.erpmodas.controller;

import com.erpmodas.dto.upload.UploadDTO;
import com.erpmodas.helpers.security.RoleAuthority;
import com.erpmodas.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final StorageService storageService;

    @PostMapping(value = "/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize(RoleAuthority.VENDEDOR_OU_PROPRIETARIO)
    public ResponseEntity<UploadDTO> upload(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam(value = "pasta", defaultValue = "produtos") String pasta) {
        String caminho = storageService.salvar(arquivo, sanitizar(pasta));
        return ResponseEntity.ok(new UploadDTO(caminho));
    }

    private String sanitizar(String pasta) {
        return Set.of("produtos", "variacoes").contains(pasta) ? pasta : "produtos";
    }
}
