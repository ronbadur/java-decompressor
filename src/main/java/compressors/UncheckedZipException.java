package compressors;

/**
 * Created by RON on 22/06/2016.
 */
public class UncheckedZipException extends RuntimeException {

    public UncheckedZipException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
