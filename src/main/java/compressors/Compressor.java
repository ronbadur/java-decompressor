package compressors;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by RON on 24/04/2016.
 */
public interface Compressor {

    void extractFiles(Path filePath, Path destinationPath) throws IOException;
}
