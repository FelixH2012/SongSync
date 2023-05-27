package de.felix.songSync.elements;

import de.felix.songSync.filesaving.FileSaving;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SFolderInfoBar extends JPanel {

    private JLabel folderLabel;
    private JLabel fileCountLabel;

    public File folder;

    public SFolderInfoBar() {
        setLayout(new BorderLayout());
        folderLabel = new JLabel();
        fileCountLabel = new JLabel();
        add(folderLabel, BorderLayout.WEST);
        add(fileCountLabel, BorderLayout.EAST);
        File selectedFolder = FileSaving.loadSettings();
        if (selectedFolder != null) {
            folder = selectedFolder;
            try {
                setFolder(selectedFolder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setFolder(File folder) throws IOException {
        this.folder = folder;
        folderLabel.setText("\uD83D\uDCC1 " + folder.getCanonicalPath());
        fileCountLabel.setText("Number of files: " + countFiles(folder));
        FileSaving.saveSettings(folder);
    }

    public void refresh(final File file) throws IOException {
        this.folder = file;
        folderLabel.setText("\uD83D\uDCC1 " + folder.getCanonicalPath());
        fileCountLabel.setText("Number of files: " + countFiles(folder));
        FileSaving.saveSettings(folder);
    }

    private int countFiles(File folder) {
        File[] files = folder.listFiles();
        if (files == null) {
            return 0;
        }

        int count = 0;
        for (File file : files) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }
}
