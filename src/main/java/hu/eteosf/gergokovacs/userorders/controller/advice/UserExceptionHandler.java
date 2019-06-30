package hu.eteosf.gergokovacs.userorders.controller.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hu.eteosf.gergokovacs.userorders.exception.ErrorObject;
import hu.eteosf.gergokovacs.userorders.exception.OrderNotFoundException;
import hu.eteosf.gergokovacs.userorders.exception.OrderUpdateException;
import hu.eteosf.gergokovacs.userorders.exception.UserCreationException;
import hu.eteosf.gergokovacs.userorders.exception.UserNotFoundException;
import hu.eteosf.gergokovacs.userorders.exception.UserUpdateException;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<Object> handleOrderNotFoundException(
            OrderNotFoundException exception, WebRequest request) {
        final ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), request.toString());
        return handleExceptionInternal(exception, errorObject, new HttpHeaders(), errorObject.getStatus(), request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleUserNotFoundException(
            UserNotFoundException exception, WebRequest request) {
        final ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), request.toString());
        return handleExceptionInternal(exception, errorObject, new HttpHeaders(), errorObject.getStatus(), request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleOrderUpdateException(
            OrderUpdateException exception, WebRequest request) {
        final ErrorObject errorObject = new ErrorObject(HttpStatus.CONFLICT, exception.getLocalizedMessage(), request.toString());
        return handleExceptionInternal(exception, errorObject, new HttpHeaders(), errorObject.getStatus(), request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleUserCreationException(
            UserCreationException exception, WebRequest request) {
        final ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_ACCEPTABLE, exception.getLocalizedMessage(), request.toString());
        return handleExceptionInternal(exception, errorObject, new HttpHeaders(), errorObject.getStatus(), request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleUserUpdateException(
            UserUpdateException exception, WebRequest request) {
        final ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_ACCEPTABLE, exception.getLocalizedMessage(), request.toString());
        return handleExceptionInternal(exception, errorObject, new HttpHeaders(), errorObject.getStatus(), request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception, WebRequest request) {
        final ErrorObject errorObject = new ErrorObject(HttpStatus.CONFLICT ,"Unique ID violation! This ID is already in use.", request.toString());
        return handleExceptionInternal(exception, errorObject, new HttpHeaders(), errorObject.getStatus(), request);
    }
}