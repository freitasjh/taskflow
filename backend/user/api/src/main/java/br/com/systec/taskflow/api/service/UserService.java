package br.com.systec.taskflow.api.service;


import br.com.systec.taskflow.api.exceptions.UserNotFoundException;
import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.commons.exceptions.BaseException;

public interface UserService {

    User save(User user) throws BaseException;

    User save(User user, String profileName) throws BaseException;

    User update(User user) throws BaseException;

    User findByUsername(String login) throws UserNotFoundException;

    User findByEmail(String email) throws UserNotFoundException;

    User findByUsernameOrEmail(String login, String email) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;
}
