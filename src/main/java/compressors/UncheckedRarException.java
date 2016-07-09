package compressors;

public class UncheckedRarException extends RuntimeException {

    public UncheckedRarException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
