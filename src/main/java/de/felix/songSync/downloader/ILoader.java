package de.felix.songSync.downloader;

import de.felix.songSync.storage.URL;
import lombok.Getter;

@Getter
public abstract class ILoader {

    private final URL url;

    public ILoader(final URL url) {
        this.url = url;
    }

    protected abstract void download();
}