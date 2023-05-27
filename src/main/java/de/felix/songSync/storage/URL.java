package de.felix.songSync.storage;

import lombok.Getter;

import java.nio.file.Path;


@Getter
public class URL {

    private String url;

    private Path outputPath;

    public URL(String url, Path outputPath) {
        this.url = url;
        this.outputPath = outputPath;
    }
}