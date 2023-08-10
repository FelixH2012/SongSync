package de.felix.songSync.util.finder;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Finder extends SimpleFileVisitor<Path> {
    private final PathMatcher matcher;
    private Path exePath = null;

    public Finder(String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            exePath = file;
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return CONTINUE;
    }

    public Path done() {
        return exePath;
    }
}
