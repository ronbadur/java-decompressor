package compressors;

public class UncheckedZipException extends RuntimeException {

    public UncheckedZipException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
