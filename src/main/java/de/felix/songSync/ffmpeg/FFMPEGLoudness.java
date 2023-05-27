package de.felix.songSync.ffmpeg;


import de.felix.songSync.SongSync;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@UtilityClass
public class FFMPEGLoudness {

    public void changeLoudnessFromFile(final File input, final File output) {
        if (input.exists() && input.isAbsolute()) {
            System.out.println("Input: " + input);
            String ffmpegPath = "src/main/executables/ffmpeg.exe";
            ProcessBuilder processBuilder = new ProcessBuilder(ffmpegPath, "-y", "-i", input.getAbsolutePath(), "-filter:a", "volume=" + 1 + "dB", output.getAbsolutePath());
            processBuilder.inheritIO();
            try {
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                System.out.println("Exit code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Nuts");
        }
    }
}