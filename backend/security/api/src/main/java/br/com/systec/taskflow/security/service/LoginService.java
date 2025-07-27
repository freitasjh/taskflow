package br.com.systec.taskflow.security.service;

import br.com.systec.taskflow.security.exceptions.LoginFailedException;
import br.com.systec.taskflow.security.vo.LoginAuthenticateVO;
import br.com.systec.taskflow.security.vo.LoginVO;

public interface LoginService {

    LoginAuthenticateVO login(LoginVO loginVO) throws LoginFailedException;
}
