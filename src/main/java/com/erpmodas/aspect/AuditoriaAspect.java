package com.erpmodas.aspect;

import com.erpmodas.helpers.auditoria.Auditar;
import com.erpmodas.model.entidades.Usuario;
import com.erpmodas.service.especial.AuditoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditoriaAspect {

    private final AuditoriaService auditoriaService;

    @AfterReturning(value = "@annotation(auditar)", returning = "result")
    public void registrarAuditoria(JoinPoint joinPoint, Auditar auditar, Object result) {
        try {
            Long usuarioId = obterUsuarioLogadoId();
            if (usuarioId == null) {
                log.warn("Tentativa de auditoria sem usuário autenticado.");
                return;
            }

            Long entidadeId = extrairIdDoResultado(result, joinPoint);
            auditoriaService.registrar(
                    usuarioId,
                    auditar.acao(),
                    auditar.entidade(),
                    entidadeId
            );
        } catch (Exception e) {
            log.error("Erro ao registrar auditoria: {}", e.getMessage(), e);
        }
    }

    private Long obterUsuarioLogadoId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof Usuario usuario)) {
            return  null;
        }
        return usuario.getId();
    }

    private Long extrairIdDoResultado(Object result, JoinPoint joinPoint) {
        Object corpo = (result instanceof ResponseEntity<?> resposta) ? resposta.getBody() : result;

        Long idDoCorpo = lerIdPorReflexao(corpo);
        if (idDoCorpo != null) {
            return idDoCorpo;
        }

        MethodSignature assinatura = (MethodSignature) joinPoint.getSignature();
        String[] nomes = assinatura.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (nomes != null) {
            for (int i = 0; i < nomes.length; i++) {
                if ("id".equals(nomes[i]) && args[i] instanceof Long id) {
                    return id;
                }
            }
        }
        return null;
    }

    private Long lerIdPorReflexao(Object objeto) {
        if (objeto == null) {
            return null;
        }
        try {
            Method getId = objeto.getClass().getMethod("getId");
            Object id = getId.invoke(objeto);
            return (id instanceof Long valor) ? valor : null;
        } catch (NoSuchMethodException e) {
            log.debug("{} não expõe getId(); entidadeId ficará nulo", objeto.getClass().getSimpleName());
            return null;
        } catch (ReflectiveOperationException e) {
            log.warn("Falha ao ler getId() de {}: {}", objeto.getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }
}
