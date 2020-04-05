package time;

public class TimeParseException extends RuntimeException {

    public TimeParseException(String message) {
        super(message);
    }

    public TimeParseException(Throwable err) {
        super(err);
    }

    public TimeParseException(String message, Throwable err) {
        super(message, err);
    }
}
