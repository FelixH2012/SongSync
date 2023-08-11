package de.felix.songSync.ffmpeg;

import de.felix.songSync.SongSync;
import de.felix.songSync.util.finder.collection.FFMPEGFinder;
import de.felix.songSync.util.finder.collection.YtDlpFinder;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FFMPEGLoudness {

    private final FFMPEGFinder ffmpegFinder = new FFMPEGFinder();

    /*
    Changes the loudness from a downloaded song file, replaces the file with the output from ffmpeg then.
     */

    public void changeLoudnessFromFile(final File input, final File output) {
        if (input.exists() && input.isAbsolute()) {
            if (SongSync.debug)
                System.out.println("Input: " + input);
            String ffmpegPath;

            if (ffmpegFinder.getPathForApplication() != null) {
                ffmpegPath = ffmpegFinder.getPathForApplication().toAbsolutePath().toString();
                System.out.println("Found FFMPEG on PC.");
            } else {
                //Todo download ffmpeg exe from server
                System.out.println("Could not find FFMPEG on Pc, downloading.");
                ffmpegPath = "src/main/executables/ffmpeg.exe";
            }
            //Process line for ffmpeg
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