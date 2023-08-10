package de.felix.songSync.util.finder.collection;

import de.felix.songSync.util.finder.Finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class YtDlpFinder {

    public static Path searchExeFile() {
        Path filePath = null;
        try {
            final Path startPath = Paths.get("C:/");
            final String pattern = "yt-dlp.exe";

            final Finder finder = new Finder(pattern);
            Files.walkFileTree(startPath, finder);
            filePath = finder.done();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

}
