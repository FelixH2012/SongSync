package de.felix.songSync.elements;

import de.felix.songSync.file.SSyncFile;
import de.felix.songSync.util.FileCounter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SFolderInfoBar extends JPanel {

    private final JLabel folderLabel;
    private final JLabel fileCountLabel;

    public File folder;

    public SFolderInfoBar() {
        setLayout(new BorderLayout());
        folderLabel = new JLabel();
        fileCountLabel = new JLabel();
        add(folderLabel, BorderLayout.WEST);
        add(fileCountLabel, BorderLayout.EAST);
        File selectedFolder = SSyncFile.loadSettings();
        if (selectedFolder != null) {
            folder = selectedFolder;
            try {
                setFolder(selectedFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setFolder(final File folder) throws IOException {
        updateFolderInfo(folder);
        SSyncFile.saveSettings(folder);
    }

    public void refresh(final File file) throws IOException {
        updateFolderInfo(file);
        SSyncFile.saveSettings(folder);
    }

    private void updateFolderInfo(final File folder) {
        this.folder = folder;
        SwingUtilities.invokeLater(() -> {
            folderLabel.setText("\uD83D\uDCC1 " + folder.getAbsolutePath());
            fileCountLabel.setText("Number of files: " + FileCounter.countFiles(folder));
        });
    }
}