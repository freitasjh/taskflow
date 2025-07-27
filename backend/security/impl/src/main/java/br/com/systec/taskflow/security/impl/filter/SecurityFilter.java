package br.com.systec.taskflow.security.impl.filter;

import br.com.systec.taskflow.commons.exceptions.AuthenticateException;
import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.security.service.AuthenticationService;
import br.com.systec.taskflow.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws BaseException {
        try {
            String token = recoverToken(request);

            if(token != null){
                String subject = tokenService.getSubject(token);
                UserDetails userDetails = authenticationService.loadUserByUsername(subject);

                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request,response);
        } catch (AccessDeniedException e) {
            log.error("Usuario sem permissão para acessar o recurso", e);
            throw new AuthenticateException("Usuario sem permissao para acessar o recurso ", e);
        } catch (Exception e){
            throw new BaseException(e.getMessage(), e);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private String recoverToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.replace("Bearer ", "");
    }
}
