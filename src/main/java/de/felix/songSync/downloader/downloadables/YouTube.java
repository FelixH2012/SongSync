package de.felix.songSync.downloader.downloadables;

import de.felix.songSync.downloader.ILoader;
import de.felix.songSync.ffmpeg.FFMPEGLoudness;
import de.felix.songSync.storage.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTube extends ILoader {
    public YouTube(String url, File file) {
        super(new URL(url, file.toPath()));
    }

    @Override
    public void download() {
        String youtubeVideoUrl = getUrl().getUrl();
        String youtubeDlPath = "src/main/executables/yt-dlp.exe";

        try {
            Path tempDirectory = Files.createTempDirectory("song-cache");
            String tempOutputPath = tempDirectory.toString() + "/%(title)s.%(ext)s";
            ProcessBuilder pb = new ProcessBuilder(youtubeDlPath, "--verbose", "--extract-audio", "--audio-format", "mp3", "mp3", youtubeVideoUrl, "-o", tempOutputPath);

            BufferedReader stdoutReader;
            Process process = pb.start();
            stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String stdoutLine;
            Pattern progressPattern = Pattern.compile("\\d+\\.\\d+%");
            Matcher progressMatcher;

            Pattern pathPattern = Pattern.compile("\\[ExtractAudio\\] Destination: (.+)");
            Matcher pathMatcher;

            String downloadPath = "";

            while ((stdoutLine = stdoutReader.readLine()) != null) {
                progressMatcher = progressPattern.matcher(stdoutLine);
                pathMatcher = pathPattern.matcher(stdoutLine);
                if (pathMatcher.find()) {
                    downloadPath = pathMatcher.group(1);
                    System.out.println("Download path: " + downloadPath);
                }
                if (progressMatcher.find()) {
                    String progress = progressMatcher.group();
                    System.out.println("Download progress: " + progress);
                } else {
                    System.out.println(stdoutLine);
                }
            }


            Path filePath = Paths.get(downloadPath);


            Path fileName = filePath.getFileName();
            String songName = fileName.toString();

            if (!process.isAlive())
                FFMPEGLoudness.changeLoudnessFromFile(new File(downloadPath), new File(getUrl().getOutputPath() + "/" + songName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}