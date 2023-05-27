package de.felix.youstick.downloader.downloadables;

import de.felix.youstick.downloader.ILoader;
import de.felix.youstick.storage.URL;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class YouTube extends ILoader {
    public YouTube() {
        super(new URL("https://www.youtube.com/watch?v=CcO3RMazRDs", new File(System.getProperty("user.home") + File.separator +"Desktop").toPath()));
    }

    @Override
    public void download() {
        String youtubeVideoUrl = getUrl().url();
        String youtubeDlPath = "src/main/dll/yt-dlp.exe";
        String outputPath = getUrl().outputPath().toString();

        try {
            ProcessBuilder pb = new ProcessBuilder(youtubeDlPath, "--verbose", youtubeVideoUrl, "-o", outputPath + "/%(title)s.%(ext)s");
            BufferedReader stdoutReader, stderrReader;
            Process process = pb.start();
            stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String stdoutLine;
            while ((stdoutLine = stdoutReader.readLine()) != null) {
                System.out.println("STDOUT: " + stdoutLine);
            }
            stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String stderrLine;
            while ((stderrLine = stderrReader.readLine()) != null) {
                System.err.println("STDERR: " + stderrLine);
            }

            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}