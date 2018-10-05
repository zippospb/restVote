package ru.zippospb.restvote.util.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class ApplicationException extends RuntimeException {

    private final ErrorType type;
    private final String msg;
    private final HttpStatus httpStatus;

    public ApplicationException(String msg, HttpStatus httpStatus) {
        this(ErrorType.APP_ERROR, msg, httpStatus);
    }

    public ApplicationException(ErrorType type, String msg, HttpStatus httpStatus) {
        super(String.format("type=%s, msg=%s", type, msg));
        this.type = type;
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    public ErrorType getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
