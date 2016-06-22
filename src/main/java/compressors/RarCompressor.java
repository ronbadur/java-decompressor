package compressors;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class RarCompressor implements Compressor {

    @Override
    public void extractFiles(Path archivePath, Path destinationPath) throws IOException {
        Optional<Archive> archiveOptional = getArchive(archivePath);

        if (archiveOptional.isPresent()) {
            archiveOptional.get().getFileHeaders().forEach((currFileHeader) -> extractHeader(destinationPath,
                                                                                             archiveOptional,
                                                                                             currFileHeader));
            archiveOptional.get().close();
        }
    }

    private Optional<Archive> getArchive(Path filePath) throws IOException {
        try {
            return Optional.ofNullable(new Archive(filePath.toFile()));
        } catch (RarException ex) {
            throw new UncheckedRarException(ex);
        }
    }

    private void extractHeader(Path destinationPath, Optional<Archive> archiveOptional, FileHeader fileHeaderToExtract) {
        try{
            if (fileHeaderToExtract.isDirectory()) {
                Files.createDirectories(destinationPath.resolve(fileHeaderToExtract.getFileNameString()));
            } else {
                extractFile(destinationPath, archiveOptional, fileHeaderToExtract);
            }
        }catch (IOException ex){
            throw new UncheckedIOException(ex);
        }
    }

    private void extractFile(Path destinationPath, Optional<Archive> archiveOptional, FileHeader currFileHeader) throws IOException {
        try {
            File fileToExtract = createFile(destinationPath, currFileHeader);
            OutputStream outputStream = new FileOutputStream(fileToExtract);

            archiveOptional.get().extractFile(currFileHeader, outputStream);
            outputStream.close();
        } catch (RarException ex) {
            throw new UncheckedRarException(ex);
        }
    }

    private File createFile(Path destinationPath, FileHeader fileHeader) throws IOException {
        Path path = destinationPath.resolve(fileHeader.getFileNameString());
        Files.createDirectories(path.getParent());
        Files.createFile(path);

        return path.toFile();
    }
}
