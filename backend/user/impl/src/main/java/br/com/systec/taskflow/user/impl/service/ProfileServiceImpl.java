package br.com.systec.taskflow.user.impl.service;

import br.com.systec.taskflow.api.model.Profile;
import br.com.systec.taskflow.api.service.ProfileService;
import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.user.impl.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;

    public ProfileServiceImpl(ProfileRepository repository) {
        this.repository = repository;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Profile findByName(String name) throws BaseException {
        return repository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("Profile not found with name: " + name));
    }
}
