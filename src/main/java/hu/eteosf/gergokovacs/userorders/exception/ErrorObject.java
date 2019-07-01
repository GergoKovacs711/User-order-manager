package hu.eteosf.gergokovacs.userorders.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ErrorObject {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ErrorObject(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
