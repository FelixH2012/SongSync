package de.felix.songSync.util.language;

import lombok.Getter;

import java.util.Locale;

/**
 * Changes the language from the program to the language of the system.
 */

public class AdaptiveLanguage {

    @Getter
    private final LanguageManager languageManager;

    public AdaptiveLanguage() {
        final Locale defaultLocale = Locale.getDefault();
        languageManager = new LanguageManager(defaultLocale);
    }
}
