package compressors;

import java.io.IOException;
import java.nio.file.Path;

public interface Compressor {

    void extractFiles(Path filePath, Path destinationPath) throws IOException;
}
