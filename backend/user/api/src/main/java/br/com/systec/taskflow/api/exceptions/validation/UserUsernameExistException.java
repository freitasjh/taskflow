package br.com.systec.taskflow.api.exceptions.validation;

import br.com.systec.taskflow.commons.exceptions.ValidatorException;
import br.com.systec.taskflow.i18n.I18nTranslate;

import java.io.Serial;

public class UserUsernameExistException extends ValidatorException {

    @Serial
    private static final long serialVersionUID = -2280993977287098433L;

    public UserUsernameExistException(String username) {
        super(I18nTranslate.toLocale("user.username.exist") + username);
    }
}
