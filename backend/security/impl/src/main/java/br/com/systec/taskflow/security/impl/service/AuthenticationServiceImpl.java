package br.com.systec.taskflow.security.impl.service;

import br.com.systec.taskflow.api.service.UserService;
import br.com.systec.taskflow.security.service.AuthenticationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsernameOrEmail(username, username);
    }
}
