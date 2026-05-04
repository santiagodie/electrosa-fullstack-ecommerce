package es.unirioja.paw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticuloNotFoundException extends RuntimeException {

    public ArticuloNotFoundException() {
    }

    public ArticuloNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
