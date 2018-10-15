package ru.zippospb.restvote.util.exception;

public class TooLateToVoteException extends RuntimeException {
    public TooLateToVoteException(String message) {
        super(message);
    }
}