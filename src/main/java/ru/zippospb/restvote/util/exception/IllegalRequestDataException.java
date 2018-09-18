package ru.zippospb.restvote.util.exception;

import javax.validation.constraints.NotNull;

public class IllegalRequestDataException extends RuntimeException{
    public IllegalRequestDataException(@NotNull String message) {
        super(message);
    }
}
