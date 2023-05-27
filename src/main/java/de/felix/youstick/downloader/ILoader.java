package de.felix.youstick.downloader;

import de.felix.youstick.storage.URL;
import lombok.Getter;

@Getter
public abstract class ILoader {

    private final URL url;

    public ILoader(final URL url) {
        this.url = url;
    }

    protected abstract void download();

}
