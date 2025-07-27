package br.com.systec.taskflow.security.service;

import br.com.systec.taskflow.security.exceptions.LoginFailedException;
import br.com.systec.taskflow.security.vo.LoginAuthenticateVO;
import org.springframework.security.core.Authentication;

public interface TokenService {

    LoginAuthenticateVO generateToken(Authentication authentication) throws LoginFailedException;

    String getSubject(String tokenJWT) throws LoginFailedException;
}
