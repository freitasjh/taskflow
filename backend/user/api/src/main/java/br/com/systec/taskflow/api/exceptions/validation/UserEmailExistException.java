package br.com.systec.taskflow.api.exceptions.validation;

import br.com.systec.taskflow.commons.exceptions.ValidatorException;
import br.com.systec.taskflow.i18n.I18nTranslate;

import java.io.Serial;

public class UserEmailExistException extends ValidatorException {

    @Serial
    private static final long serialVersionUID = 1834506112021213262L;

    public UserEmailExistException(String email) {
        super(I18nTranslate.toLocale("user.email.exist") + email);
    }
}
