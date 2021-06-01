package exceptions;

public class PersistentException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public PersistentException(String errorCode, String message) {
        super(errorCode + " " + message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
