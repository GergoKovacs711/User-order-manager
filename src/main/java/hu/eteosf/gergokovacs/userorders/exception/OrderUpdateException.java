package hu.eteosf.gergokovacs.userorders.exception;

public class OrderUpdateException extends RuntimeException {
    public OrderUpdateException() {
        super();
    }

    public OrderUpdateException(String message) {
        super(message);
    }

    public OrderUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderUpdateException(Throwable cause) {
        super(cause);
    }

    protected OrderUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
