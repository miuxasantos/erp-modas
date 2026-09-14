package com.erpmodas.service.security;

import com.erpmodas.dto.autenticacao.LoginRequestDTO;
import com.erpmodas.dto.autenticacao.LoginResponseDTO;
import com.erpmodas.dto.usuario.UsuarioResponseDTO;
import com.erpmodas.enums.TipoAcaoAud;
import com.erpmodas.mapper.UsuarioMapper;
import com.erpmodas.model.entidades.SessionToken;
import com.erpmodas.model.entidades.Usuario;
import com.erpmodas.repository.SessionTokenRepository;
import com.erpmodas.repository.UsuarioRepository;
import com.erpmodas.service.especial.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final SessionTokenRepository sessionTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioMapper usuarioMapper;
    private final AuditoriaService auditoriaService;

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos."));
        System.out.println("1 - USUARIO ENCONTRADO");
        if(!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            System.out.println("2 - SENHA INCORRETA");
            auditoriaService.registrar(
                    usuario.getId(),
                    TipoAcaoAud.LOGIN_FALHA,
                    "Login",
                    null
            );

            throw new RuntimeException("E-mail ou senha inválidos.");
        }

        System.out.println("3 - SENHA CORRETA");
        auditoriaService.registrar(
                usuario.getId(),
                TipoAcaoAud.LOGIN,
                "Login",
                null
        );

        System.out.println("4 - AUDITORIA REGISTRADA");

        String token = jwtService.gerarToken(usuario);

        System.out.println("5 - TOKEN GERADO");

        SessionToken sessao = SessionToken.builder()
            .token(token)
            .usuario(usuario)
            .dataCriacao(LocalDateTime.now())
            .dataExp(LocalDateTime.now().plusHours(8))
            .ativo(true)
            .build();

        sessionTokenRepository.save(sessao);

        System.out.println("6 - SESSAO SALVA");

        UsuarioResponseDTO usuarioDTO = usuarioMapper.toDTO(usuario);
        return new LoginResponseDTO(
            token,
            "Bearer",
            usuarioDTO,
            8L * 60 * 60
        );
    }

    @Transactional
    public void logout(String token) {
        sessionTokenRepository.findByToken(token)
                .ifPresent(SessionToken::invalidar);
    }

}
