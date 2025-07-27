package br.com.systec.taskflow.api.service;

import br.com.systec.taskflow.api.model.Profile;
import br.com.systec.taskflow.commons.exceptions.BaseException;

public interface ProfileService {

    Profile findByName(String name) throws BaseException;
}
