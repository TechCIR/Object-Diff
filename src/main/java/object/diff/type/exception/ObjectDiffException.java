package object.diff.type.exception;

public class ObjectDiffException extends Exception {

    String message;

    public ObjectDiffException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
