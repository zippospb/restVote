package ru.zippospb.restvote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.zippospb.restvote.util.ValidationUtil;
import ru.zippospb.restvote.util.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static ru.zippospb.restvote.util.exception.ErrorType.DATA_ERROR;
import static ru.zippospb.restvote.util.exception.ErrorType.REVOTE_ERROR;
import static ru.zippospb.restvote.util.exception.ErrorType.VALIDATION_ERROR;

@RestControllerAdvice
public class ExceptionInfoHandler {
    private final static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorInfo> applicationError(HttpServletRequest req, ApplicationException appEx) {
        ErrorInfo errorInfo = logAndGetErrorInfo(req, appEx, false, appEx.getType(), appEx.getMsg());
        return new ResponseEntity<>(errorInfo, appEx.getHttpStatus());
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fe -> {
                    String msg = fe.getDefaultMessage();
                    return msg == null ? null : (msg.startsWith(fe.getField())) ? msg : fe.getField() + ' ' + msg;
                }).filter(Objects::nonNull)
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, rootMsg);
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(TooLateToVoteException.class)
    public ErrorInfo tooLateToVoteError(HttpServletRequest req, Exception e){
        return logAndGetErrorInfo(req, e, false, REVOTE_ERROR);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType,
                errorType.getErrorMsg(),
                details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});
    }

}
