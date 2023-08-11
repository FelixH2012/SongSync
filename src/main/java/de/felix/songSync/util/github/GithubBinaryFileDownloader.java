//https://stackoverflow.com/questions/13441720/download-binary-file-from-github-using-java
package de.felix.songSync.util.github;

import lombok.experimental.UtilityClass;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@UtilityClass
public class GithubBinaryFileDownloader {

    private static final int BUFFER_SIZE = 4096;

    /**
     * Basically checks with help from the website header if we got redirected, other headers than 301 & 302 are
     * counted as errors & false the redirect boolean, so we won't continue trying to download the binary file from github.
     */
    private static boolean isRedirected(final Map<String, List<String>> header) {
        for (String hv : header.get(null))
            if (hv.contains(" 301 ") || hv.contains(" 302 ")) return true;
        return false;
    }

    private URL getFinalUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setInstanceFollowRedirects(false);
        Map<String, List<String>> header = http.getHeaderFields();
        while (isRedirected(header)) {
            urlStr = header.get("Location").get(0);
            url = new URL(urlStr);
            http = (HttpURLConnection) url.openConnection();
            header = http.getHeaderFields();
        }
        return url;
    }

    private void downloadFileFromUrl(URL url, String fileName) throws IOException {
        try (InputStream input = url.openStream();
             OutputStream output = Files.newOutputStream(Paths.get(fileName))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
        }
    }
}
