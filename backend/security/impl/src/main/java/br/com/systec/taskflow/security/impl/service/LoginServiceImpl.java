package br.com.systec.taskflow.security.impl.service;

import br.com.systec.taskflow.security.exceptions.LoginFailedException;
import br.com.systec.taskflow.security.service.LoginService;
import br.com.systec.taskflow.security.service.TokenService;
import br.com.systec.taskflow.security.vo.LoginAuthenticateVO;
import br.com.systec.taskflow.security.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);;

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public LoginAuthenticateVO login(LoginVO loginVO) throws LoginFailedException {
        try {
            var userAndPasswordToken = new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword());

            var authenticate = authenticationManager.authenticate(userAndPasswordToken);

            return tokenService.generateToken(authenticate);
        } catch (Exception e) {
            log.error("Login failed for user {}: {}", loginVO.getUsername(), e.getMessage(), e);
            throw new LoginFailedException("Login failed");
        }
    }
}
