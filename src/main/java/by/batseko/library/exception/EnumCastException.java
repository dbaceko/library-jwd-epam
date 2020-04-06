package by.batseko.library.exception;

public class EnumCastException extends Exception {
    public EnumCastException() {
    }

    public EnumCastException(String message) {
        super(message);
    }

    public EnumCastException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumCastException(Throwable cause) {
        super(cause);
    }

    public EnumCastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

