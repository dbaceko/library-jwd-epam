package by.batseko.library.exception;

public class ControllerRuntimeException extends RuntimeException {
    public ControllerRuntimeException() {
    }

    public ControllerRuntimeException(String message) {
        super(message);
    }

    public ControllerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerRuntimeException(Throwable cause) {
        super(cause);
    }

    public ControllerRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
