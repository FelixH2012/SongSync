package de.felix.songSync.filesaving;

import de.felix.songSync.data.ChangeLoudness;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class FileSaving {

    public void saveSettings(File selectedFolder) {
        Properties properties = new Properties();
        properties.setProperty("selectedFolder", selectedFolder.getAbsolutePath());
        properties.setProperty("SameLoudnessLevel", ChangeLoudness.canChangeLoudness ? "true" : "false");
        try (FileOutputStream outputStream = new FileOutputStream("settings.properties")) {
            properties.store(outputStream, "App settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File loadSettings() {
        Properties properties = new Properties();
        File selectedFolder = null;

        try (FileInputStream inputStream = new FileInputStream("settings.properties")) {
            properties.load(inputStream);
            String folderPath = properties.getProperty("selectedFolder");
            String loudnessChangeStatus = properties.getProperty("SameLoudnessLevel");

            System.out.println(loudnessChangeStatus);

            if (folderPath != null) {
                selectedFolder = new File(folderPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return selectedFolder;
    }


}