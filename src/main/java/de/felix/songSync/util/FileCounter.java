package de.felix.songSync.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@UtilityClass
public class FileCounter {

    public int countFiles(File folder) {
        try {
            return (int) Files.list(folder.toPath())
                    .filter(Files::isRegularFile)
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
