package de.felix.songSync.ffmpeg;

import de.felix.songSync.SongSync;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FFMPEGLoudness {

    public void changeLoudnessFromFile(final File input, final File output) {
        if (input.exists() && input.isAbsolute()) {
            if (SongSync.debug)
                System.out.println("Input: " + input);
            final String ffmpegPath = "src/main/executables/ffmpeg.exe"; //probably not the best way, you should first search for a ffmpeg application on the pc
            //Then, if nothing has been found, proceed to download the ffmpeg exe, so the program is not taking space without any reason //todo
            ProcessBuilder processBuilder = new ProcessBuilder(ffmpegPath, "-y", "-i", input.getAbsolutePath(), "-filter:a", "volume=" + 1 + "dB", output.getAbsolutePath());
            processBuilder.inheritIO();
            try {
                final Process process = processBuilder.start();
                final int exitCode = process.waitFor();
                System.out.println("Exit code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Nuts");
        }
    }
}