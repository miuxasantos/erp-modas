package com.erpmodas.service;

import com.erpmodas.dto.limpeza.LimpezaResultDTO;
import com.erpmodas.repository.ProdutoRepository;
import com.erpmodas.repository.apoio.VariacaoProdutoRepository;
import com.erpmodas.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class LimpezaService {

    private final ProdutoRepository produtoRepository;
    private final VariacaoProdutoRepository variacaoProdutoRepository;
    private final StorageService storageService;

    private static final Duration IDADE_MINIMA = Duration.ofHours(72);

    @Scheduled(cron = "${app.storage.limpeza-cron}")
    public void executarAgendado() {
        LimpezaResultDTO l = limparOrfaos(false);
        log.info("Limpeza semanal: {} órfãos removidos {} KB liberados.", l.getRemovidos(), l.getBytesLiberados()/2024);
    }

    public LimpezaResultDTO limparOrfaos(boolean simulacao) {
        Set<String> emUso = carregarCaminhosEmUso();
        Path base = storageService.base();

        if(!Files.isDirectory(base)) {
            log.warn("Diretório de uploads não existe: {}", base);
            return new LimpezaResultDTO(0, emUso.size(), 0, 0, 0L, List.of());
        }

        Instant corte = Instant.now().minus(IDADE_MINIMA);
        int total = 0, orfaos = 0, removidos = 0;
        long bytes = 0L;
        List<String> caminhos = new java.util.ArrayList<>();

        try(Stream<Path> arquivos = Files.walk(base)) {
            for (Path arquivo : arquivos.filter(Files::isRegularFile).toList()) {
                total++;

                String relativo = paraCaminhorelativo(base, arquivo);
                if (emUso.contains(relativo)) continue;

                BasicFileAttributes attrs = Files.readAttributes(arquivo, BasicFileAttributes.class);
                if (attrs.creationTime().toInstant().isAfter(corte)) continue;

                orfaos++;
                caminhos.add(relativo);
                long tamanho = attrs.size();

                if (!simulacao) {
                    try {
                        Files.delete(arquivo);
                        removidos++;
                        bytes += tamanho;
                    } catch (IOException e) {
                        log.warn("Falha ao remover órfão {}: {}", relativo, e.getMessage());
                    }
                } else {
                    bytes += tamanho;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Falha ao varrer o diretório de imagens.", e);
        }

        return new LimpezaResultDTO(total, emUso.size(), orfaos, removidos, bytes, caminhos);
    }

    public Set<String> carregarCaminhosEmUso() {
        Set<String> emUso = new HashSet<>();

        Stream.concat(
                produtoRepository.findAllCaminhosImagem().stream(),
                variacaoProdutoRepository.findAllCaminhosImagem().stream()
        ).forEach(caminho -> {
            emUso.add(caminho);
            emUso.add(StorageService.caminhoThumb(caminho));
        });

        return emUso;
    }

    private String paraCaminhorelativo(Path base, Path arquivo) {
        return base.relativize(arquivo).toString().replace('\\', '/');
    }
}
