package ru.zippospb.restvote.util.exception;

import org.springframework.http.HttpStatus;

public class TooLateToVoteException extends ApplicationException {
    public TooLateToVoteException(String message) {
        super(ErrorType.REVOTE_ERROR, message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}