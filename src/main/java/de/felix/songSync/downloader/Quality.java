package de.felix.songSync.downloader;

public enum Quality {

    HIGHEST("libopus"), HIGH("libmp3lame"), MEDIUM("aac"), LOW("vorbis"), VERY_LOW("mp2");

    final String codecName;

    Quality(final String codecName) {
        this.codecName = codecName;
    }

}
