package in.sikkim_allied_health_care_council.server.exceptions;

/** Thrown when an uploaded file is missing, too large, or an unsupported type. */
public class InvalidFileException extends RuntimeException {

    public InvalidFileException(String message) {
        super(message);
    }
}
