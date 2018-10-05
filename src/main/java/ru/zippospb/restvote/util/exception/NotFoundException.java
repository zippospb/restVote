package ru.zippospb.restvote.util.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {
    private static final String NOT_FOUND_EXCEPTION = "Not found entity with %s";

    public NotFoundException(String arg) {
        super(ErrorType.DATA_NOT_FOUND, String.format(NOT_FOUND_EXCEPTION, arg), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
