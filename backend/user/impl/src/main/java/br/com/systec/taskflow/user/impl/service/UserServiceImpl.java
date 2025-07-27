package br.com.systec.taskflow.user.impl.service;

import br.com.systec.taskflow.api.exceptions.UserNotFoundException;
import br.com.systec.taskflow.api.exceptions.validation.UserEmailExistException;
import br.com.systec.taskflow.api.exceptions.validation.UserUsernameExistException;
import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.api.service.ProfileService;
import br.com.systec.taskflow.api.service.UserService;
import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.user.impl.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;
    private final ProfileService profileService;

    public UserServiceImpl(UserRepository repository, ProfileService profileService) {
        this.repository = repository;
        this.profileService = profileService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user, String profileName) throws BaseException {
        try {
            validateLoginAndEmailExist(user.getUsername(), user.getEmail());

            if (user.getProfile() == null || user.getProfile().isEmpty()) {
                user.addProfile(profileService.findByName(profileName));
            }

            return repository.save(user);
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage(), e);
            throw new BaseException("Error saving user", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) throws BaseException {
        try {
            validateLoginAndEmailExist(user.getUsername(), user.getEmail());

            return repository.save(user);
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage(), e);
            throw new BaseException("Error saving user", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User user) throws BaseException {
        try {
            return repository.update(user);
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage(), e);
            throw new BaseException("Error updating user", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findByUsername(String username) throws UserNotFoundException {
        try {
           return repository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        } catch (BaseException e) {
            log.error("Error finding user by username: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error finding user by username: {}", e.getMessage(), e);
            throw new BaseException("Error finding user by username", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findByEmail(String email) throws UserNotFoundException {
        try {
            return repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        } catch (BaseException e) {
            log.error("Error finding user by username: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error finding user by username: {}", e.getMessage(), e);
            throw new BaseException("Error finding user by username", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findByUsernameOrEmail(String username, String email) throws UserNotFoundException {
        try {
            return repository.findByUsernameOrEmail(username, email).orElseThrow(UserNotFoundException::new);
        } catch (BaseException e) {
            log.error("Error finding user by username: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error finding user by username: {}", e.getMessage(), e);
            throw new BaseException("Error finding user by username", e);
        }
    }


    private void validateLoginAndEmailExist(String username, String email) throws BaseException {
        Optional<User> existingUser = repository.findByUsernameOrEmail(username, email);

        if(existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getUsername().equals(username)) {
                throw new UserUsernameExistException(username);
            }

            if (user.getEmail().equals(email)) {
                throw new UserEmailExistException(email);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findById(Long id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
