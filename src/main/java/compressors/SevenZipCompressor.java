package compressors;

import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class SevenZipCompressor implements Compressor {

    @Override
    public void extractFiles(Path filePath, Path destinationPath) throws IOException {
        ISimpleInArchive simpleInArchive = getSimpleArchive(filePath);
        Arrays.asList(simpleInArchive.getArchiveItems()).forEach((currItem) -> extractItem(currItem, destinationPath));
        simpleInArchive.close();
    }

    private void extractItem(ISimpleInArchiveItem itemToExtract, Path destinationPath) {
        try{
            if (itemToExtract.isFolder()) {
                Files.createDirectories(destinationPath.resolve(itemToExtract.getPath()));
            }
            else{
                extractFile(itemToExtract, destinationPath);
            }
        } catch (IOException ex){
            throw new UncheckedIOException(ex);
        }
    }

    private void extractFile(ISimpleInArchiveItem itemToExtract, Path destinationPath) {
        try {
            ExtractOperationResult result = itemToExtract.extractSlow(data -> writeFileContent(data,itemToExtract, destinationPath));

            if (result != ExtractOperationResult.OK){
                throw new UncheckedSevenZipException(new SevenZipException());
            }
        } catch (SevenZipException e) {
            throw new UncheckedSevenZipException(e);
        }
    }

    private int writeFileContent(byte[] data, ISimpleInArchiveItem itemToExtract, Path destinationPath) {
        try {
            Path pathToExtractedItem = destinationPath.resolve(itemToExtract.getPath());
            Files.createDirectories(pathToExtractedItem.getParent());
            Files.createFile(pathToExtractedItem);
            FileOutputStream fileOutputStream = new FileOutputStream(pathToExtractedItem.toFile());
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return data.length;
    }

    private ISimpleInArchive getSimpleArchive(Path sourcePath) {
        IInArchive archive;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(sourcePath.toFile(), "r");
            archive = SevenZip.openInArchive(ArchiveFormat.SEVEN_ZIP, new RandomAccessFileInStream(randomAccessFile));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return archive.getSimpleInterface();
    }

}
