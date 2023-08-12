package de.felix.songSync.downloader.downloadables;

import de.felix.songSync.downloader.ILoader;
import de.felix.songSync.downloader.Quality;
import de.felix.songSync.ffmpeg.FFMPEGLoudness;
import de.felix.songSync.storage.URL;
import de.felix.songSync.util.finder.collection.YtDlpFinder;

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
    private final YtDlpFinder ytDlpFinder = new YtDlpFinder();

    private String songName = "", downloadPath = "";

    public YouTube(String url, File file) {
        super(new URL(url, file.toPath()));
    }

    @Override
    public void download() {
        String youtubeVideoUrl = getUrl().getUrl();
        String youtubeDlPath = getYoutubeDlPath();

        try {
            final Quality quality = Quality.HIGHEST;
            final Path tempDirectory = Files.createTempDirectory("song-cache");
            final String tempOutputPath = tempDirectory.toString() + "/%(title)s.%(ext)s";

            final ProcessBuilder pb = createProcessBuilder(youtubeDlPath, quality, youtubeVideoUrl, tempOutputPath);
            final Process process = pb.start();

            monitorDownloadProgress(process);

            if (!process.isAlive())
                FFMPEGLoudness.changeLoudnessFromFile(new File(downloadPath), new File(getUrl().getOutputPath() + "/" + songName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getYoutubeDlPath() {
        String youtubeDlPath;
        if (ytDlpFinder.getPathForApplication() != null) {
            youtubeDlPath = ytDlpFinder.getPathForApplication().toAbsolutePath().toString();
            System.out.println("Found YT-DLP on PC.");
        } else {
            System.out.println("Could not find YT-DLP on Pc, downloading.");
            youtubeDlPath = "src/main/executables/yt-dlp.exe";
        }
        return youtubeDlPath;
    }

    private ProcessBuilder createProcessBuilder(String youtubeDlPath, Quality quality, String youtubeVideoUrl, String tempOutputPath) {
        return new ProcessBuilder(youtubeDlPath, "--verbose", "--extract-audio", "--audio-format", "mp3", "--audio-codec", quality.name(), youtubeVideoUrl, "-o", tempOutputPath);
    }

    private void monitorDownloadProgress(Process process) throws IOException {
        final BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String stdoutLine;
        final Pattern progressPattern = Pattern.compile("\\d+\\.\\d+%");
        Matcher progressMatcher;
        Pattern pathPattern = Pattern.compile("\\[ExtractAudio\\] Destination: (.+)");
        Matcher pathMatcher;


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

        final Path filePath = Paths.get(downloadPath);
        final Path fileName = filePath.getFileName();
        songName = fileName.toString();
    }
}