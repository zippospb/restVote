package ru.zippospb.restvote.util.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class ApplicationException extends RuntimeException {

    private final ErrorType type;
    private final String msg;
    private final HttpStatus httpStatus;
    private final String[] args;

    public ApplicationException(String msg, HttpStatus httpStatus) {
        this(ErrorType.APP_ERROR, msg, httpStatus);
    }

    public ApplicationException(ErrorType type, String msg, HttpStatus httpStatus, String... args) {
        super(String.format("type=%s, msg=%s, args=%s", type, msg, Arrays.toString(args)));
        this.type = type;
        this.msg = msg;
        this.httpStatus = httpStatus;
        this.args = args;
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

    public String[] getArgs() {
        return args;
    }
}
