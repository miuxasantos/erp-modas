package com.erpmodas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/uploads")
public class UploadController {
    @PostMapping("/imagem")
    public ResponseEntity<Map<String, String>> uploadImagem(
            @RequestParam("arquivo") MultipartFile arquivo
    ) {
        // salva em /uploads/produtos/
        // retorna a URL pública
        return ResponseEntity.ok(Map.of("url", ""));
    }
}
