package br.com.systec.taskflow.commons.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StandardError> errorServerException(Exception e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<StandardError> baseException(BaseException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
        err.setDetailMessage(e.getDetailMessage());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(e.getHttpStatus() != null){
            httpStatus = e.getHttpStatus();
        }

        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseEntity<StandardError> authenticateException(AuthenticateException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(HttpStatus.NOT_ACCEPTABLE.value(),"error.validation", System.currentTimeMillis());
        for(FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> runtimeException(RuntimeException e, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = e.getMessage() != null ? e.getMessage() : "Erro generico";

        if(e instanceof AuthorizationDeniedException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            message = "Usuário sem permissão para acessar o recurso.";
        }

        StandardError err = new StandardError(httpStatus.value(), message, System.currentTimeMillis());

        return ResponseEntity.status(httpStatus).body(err);
    }
}
