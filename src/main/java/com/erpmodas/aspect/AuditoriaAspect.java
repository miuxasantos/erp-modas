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
        if(auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof Usuario)) {
            return  null;
        }

        Usuario usuario = (Usuario) auth.getPrincipal();
        return usuario.getId();
    }

    private Long extrairIdDoResultado(Object result, JoinPoint joinPoint) {
        if(result != null) {
            try {
                Method getId = result.getClass().getMethod("getId");
                Object id = getId.invoke(result);
                if(id instanceof Long) {
                    return (Long) id;
                }
            } catch (Exception ignored) {
            }
        }

        Object[] args = joinPoint.getArgs();
        if(args.length > 0 && args[0] instanceof Long) {
            return (Long) args[0];
        }

        return null;
    }
}
