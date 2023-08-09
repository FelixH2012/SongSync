package de.felix.songSync.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@UtilityClass
public class FileCounter {

    public int countFiles(File folder) throws IOException {
        try (Stream<Path> files = Files.list(folder.toPath())) {
            return (int) files.filter(Files::isRegularFile).count();
        }
    }
}