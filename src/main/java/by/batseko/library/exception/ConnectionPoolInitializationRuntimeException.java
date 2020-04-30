package by.batseko.library.exception;

public class ConnectionPoolInitializationRuntimeException extends RuntimeException {
    public ConnectionPoolInitializationRuntimeException() {
    }

    public ConnectionPoolInitializationRuntimeException(String message) {
        super(message);
    }

    public ConnectionPoolInitializationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolInitializationRuntimeException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolInitializationRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
