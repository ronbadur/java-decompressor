package compressors;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.nio.file.Path;

/**
 * Created by RON on 24/04/2016.
 */
public class ZipCompressor implements Compressor {

    @Override
    public void extractFiles(Path filePath, Path destinationPath) {
        try {
            ZipFile zipFile = new ZipFile(filePath.toFile());
            zipFile.extractAll(destinationPath.toString());
        } catch (ZipException ex) {
            throw new UncheckedZipException(ex);
        }
    }
}
