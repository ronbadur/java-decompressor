package compressors;

/**
 * Created by RON on 22/06/2016.
 */
public class UncheckedRarException extends RuntimeException {

    public UncheckedRarException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
