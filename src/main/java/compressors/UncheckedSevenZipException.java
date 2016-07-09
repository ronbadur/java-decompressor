package compressors;

public class UncheckedSevenZipException extends RuntimeException {

    public UncheckedSevenZipException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
