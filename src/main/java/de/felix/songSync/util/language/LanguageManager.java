package de.felix.songSync.util.language;

import java.util.*;

public class LanguageManager {
    private static LanguageManager instance;
    private Locale currentLocale;
    private ResourceBundle currentBundle;
    LanguageManager(Locale defaultLocale) {
        this.currentLocale = defaultLocale;
        this.currentBundle = ResourceBundle.getBundle("languages.messages", defaultLocale);
    }

    public static synchronized LanguageManager getInstance(Locale defaultLocale) {
        if (instance == null)
            instance = new LanguageManager(defaultLocale);
        return instance;
    }

    public void changeLanguage(Locale locale) {
        this.currentLocale = locale;
        this.currentBundle = ResourceBundle.getBundle("messages", locale);
    }

    public String getMessage(String key) {
        return currentBundle.getString(key);
    }
}