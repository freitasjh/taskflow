package br.com.systec.taskflow.security.impl.interceptors;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.commons.exceptions.AuthenticateException;
import br.com.systec.taskflow.security.permission.HasPermission;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class PermissionInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        // Verifica se tem a anotação @HasPermission
        HasPermission permissionAnnotation = handlerMethod.getMethodAnnotation(HasPermission.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = validateUserIsAdmin(authentication);
        boolean hasPermissionAnnotation = permissionAnnotation != null;

        if (!authentication.isAuthenticated()) {
            log.warn("Usuário não autenticado, bloqueando acesso.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Acesso negado: usuário não autenticado.");
            return false;
        }

        if (!hasPermissionAnnotation && isAdmin) {
            return true;
        } else if(hasPermissionAnnotation && isAdmin){
            return true;
        } else if(!hasPermissionAnnotation) {
            throw new AuthenticateException("Usuário sem permissão para acessar o recurso.");
        }

        String permission = permissionAnnotation.value();
        log.warn("Usuario autenticado: {}", authentication.getName());
        log.warn("Usuario authenticado com profile: {}", authentication.getAuthorities());
        log.warn("Validando permissão: {}", permission);

        boolean allowed = authentication.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equalsIgnoreCase(permission));

        if(!allowed) {
            log.warn("Usuario comum sem permissão para acessar o recurso: {}", permission);
            throw new AuthenticateException("Usuário sem permissão para acessar o recurso.");
        }

        return true;
    }

    private boolean validateUserIsAdmin(Authentication authentication) {
        if (authentication.getPrincipal() instanceof User userDetails) {
            return userDetails.getProfile().stream()
                    .anyMatch(profile -> profile.getName().contains("ADMIN"));
        }

        return false;
    }
}
