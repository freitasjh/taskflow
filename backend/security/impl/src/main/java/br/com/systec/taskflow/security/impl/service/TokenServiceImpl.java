package br.com.systec.taskflow.security.impl.service;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.security.exceptions.LoginFailedException;
import br.com.systec.taskflow.security.service.TokenService;
import br.com.systec.taskflow.security.vo.LoginAuthenticateVO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String ISSUE = "taskflow.api";

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public LoginAuthenticateVO generateToken(Authentication authentication) throws LoginFailedException {
        User user = (User) authentication.getPrincipal();
        String token = JWT.create()
                .withIssuer(ISSUE)
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withExpiresAt(generateDateExpired())
                .sign(getAlgorithm());

        return new LoginAuthenticateVO(user.getId(), token, "Bearer", user.getProfile().stream().iterator().next().getName());
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getSubject(String tokenJWT) throws LoginFailedException {
        try {
            JWT.require(getAlgorithm()).build().verify(tokenJWT);
            return JWT.require(getAlgorithm())
                    .withIssuer(ISSUE)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (TokenExpiredException e){
            throw new LoginFailedException("Token expired");
        }catch (Exception e){
            throw new BaseException(e.getMessage(), e);
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Instant generateDateExpired() {
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00"));
    }
}
