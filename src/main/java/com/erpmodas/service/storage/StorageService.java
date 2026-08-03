package com.erpmodas.service.storage;

import com.erpmodas.helpers.storage.StorageProperties;
import com.erpmodas.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {

    private final StorageProperties storageProperties;

    public static final String PASTA_THUMB = "thumb";

    private static final Map<String, String> TIPOS_PERMITIDOS = Map.of(
            "image/jpeg", "jpg",
            "image/png", "jpg",
            "image/webp", "jpg"
    );

    private static final Set<String> PASTAS_VALIDAS = Set.of("produtos", "variacoes");
    private final ProdutoService produtoService;

    public String salvar(MultipartFile arquivo, String subpasta) {
        validar(arquivo, subpasta);

        String extensao = TIPOS_PERMITIDOS.get(arquivo.getContentType());
        String nomeArquivo = UUID.randomUUID() + extensao;
        String caminhoRelativo = subpasta + "/" + nomeArquivo;

        Path destinoFull = resolverSeguro(caminhoRelativo);
        Path destinoThumb = resolverSeguro(caminhoThumb(caminhoRelativo));

        try {
            Files.createDirectories(destinoFull.getParent());
            Files.createDirectories(destinoThumb.getParent());

            int[] dim = dimensoesDe(arquivo);

            gerarVariante(arquivo, destinoFull, dim, storageProperties.getLarguraFull(), storageProperties.getQualidade());
            gerarVariante(arquivo, destinoThumb, dim, storageProperties.getLarguraThumb(), 0.80f);

            return caminhoRelativo;
        } catch (IOException e) {
           limparSilencioso(destinoFull);
           limparSilencioso(destinoThumb);
           throw new RuntimeException("Falha ao salvar a imagem", e);
        }
    }

    public void remover(String caminhoRelativo) {
        if(caminhoRelativo == null || caminhoRelativo.isBlank()) return;

        limparSilencioso(resolverSeguro(caminhoRelativo));
        limparSilencioso(resolverSeguro(caminhoRelativo));
    }

    public static String caminhoThumb(String caminhoFull) {
        int barra = caminhoFull.lastIndexOf("/");
        return caminhoFull.substring(0, barra + 1)
                + PASTA_THUMB + "/"
                + caminhoFull.substring(barra + 1);
    }

    private void gerarVariante(MultipartFile arquivo,
                               Path destino,
                               int[]dimOriginal,
                               int larguraMax,
                               Float qualidade) throws IOException {

        int largura = Math.min(dimOriginal[0], larguraMax);
        int altura = Math.min(dimOriginal[1], larguraMax);

        try (InputStream in = arquivo.getInputStream()) {
            Thumbnails.of(in)
                    .size(largura, altura)
                    .keepAspectRatio(true)
                    .outputFormat("jpg")
                    .outputQuality(qualidade)
                    .toFile(destino.toFile());
            }
    }

    private int[] dimensoesDe(MultipartFile arquivo) throws IOException {
        try(InputStream in = arquivo.getInputStream()) {
            BufferedImage img = ImageIO.read((in));
            if(img == null) {
                throw new RuntimeException("Esse arquivo não é uma imagem válida.");
            }

            return new int[] {
                    img.getWidth(), img.getHeight()
            };
        }
    }

    private void validar(MultipartFile arquivo, String subpasta) {
        if(arquivo == null || arquivo.isEmpty()) {
            throw new RuntimeException("Arquivo vazio.");
        }

        if(arquivo.getSize() > storageProperties.getTamanhoMax().toBytes()) {
            throw new RuntimeException("O arquivo excede o tamanho máximo permitido");
        }

        if(!TIPOS_PERMITIDOS.containsKey(arquivo.getContentType())) {
            throw new RuntimeException("Formato inválido, use PNG, JPG ou WEBP.");
        }

        if(!PASTAS_VALIDAS.contains(subpasta)) {
            throw new RuntimeException("Destino inválida.");
        }
    }

    private Path resolverSeguro(String caminhoRelativo) {
        Path base = base();
        Path alvo = base.resolve(caminhoRelativo).normalize();

        if(!alvo.startsWith(base)) {
            throw new RuntimeException("Esse destino é inválido.");
        }
        return alvo;
    }

    private void limparSilencioso(Path p) {
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {
            log.warn("Não foi possível remover {}: {}", p, e.getMessage());
        }
    }

    public Path base() {
        return Paths.get(storageProperties.getDir()).toAbsolutePath().normalize();
    }
}
