package de.felix.songSync.file;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class SSyncFile {

    private static final String SETTINGS_FILE_PATH = "settings.properties";
    private static final String SELECTED_FOLDER_PROPERTY = "selectedFolder";

    public void saveSettings(File selectedFolder) {
        Properties properties = new Properties();
        properties.setProperty(SELECTED_FOLDER_PROPERTY, selectedFolder.getAbsolutePath());

        try (FileOutputStream outputStream = new FileOutputStream(SETTINGS_FILE_PATH)) {
            properties.store(outputStream, "App settings");
        } catch (IOException e) {
            System.err.println("Failed to save settings: " + e.getMessage());
        }
    }

    public File loadSettings() {
        Properties properties = new Properties();
        File selectedFolder = null;

        try (FileInputStream inputStream = new FileInputStream(SETTINGS_FILE_PATH)) {
            properties.load(inputStream);
            final String folderPath = properties.getProperty(SELECTED_FOLDER_PROPERTY);

            if (folderPath != null) {
                final File folder = new File(folderPath);
                if (folder.exists()) {
                    selectedFolder = folder;
                } else {
                    System.err.println("The selected folder does not exist: " + folderPath);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load settings: " + e.getMessage());
        }

        return selectedFolder;
    }
}