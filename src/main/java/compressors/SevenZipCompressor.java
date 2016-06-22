package compressors;

import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

/**
 * This code is still in developing , please don't use it.
 */
public class SevenZipCompressor implements Compressor {

    @Override
    public void extractFiles(Path filePath, Path destinationPath) throws IOException {
        IInArchive archive;
        RandomAccessFile randomAccessFile;

        randomAccessFile = new RandomAccessFile(filePath.toFile(), "r");

        archive = SevenZip.openInArchive(ArchiveFormat.SEVEN_ZIP, // null - autodetect
                new RandomAccessFileInStream(
                        randomAccessFile));

        int numberOfItems = archive.getNumberOfItems();

        archive.close();
        randomAccessFile.close();

        System.out.println(numberOfItems);
    }
}
