package ru.zippospb.restvote.util.exception;

public enum ErrorType {
    APP_ERROR("Application error"),
    DATA_NOT_FOUND("Data not found"),
    DATA_ERROR("Data error"),
    VALIDATION_ERROR("Validation error"),
    REVOTE_ERROR("Re-vote error");

    private final String errorCode;

    ErrorType(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorCode;
    }
}
