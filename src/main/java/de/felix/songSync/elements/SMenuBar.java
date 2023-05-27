package de.felix.songSync.elements;

import de.felix.songSync.SongSync;
import de.felix.songSync.setting.SettingUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SMenuBar {
    private JMenuBar menuBar;

    public SMenuBar(JFrame frame) {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);



     //   JMenuItem settingsMenuItem = new JMenuItem("Open settings");
      //  settingsMenu.add(settingsMenuItem);

        JMenuItem openFolderMenuItem = new JMenuItem("Select song folder");
        fileMenu.add(openFolderMenuItem);

        JMenuItem openFolder = new JMenuItem("Open Song Folder");

        fileMenu.add(openFolder);

        openFolder.addActionListener(e -> {
            if (SongSync.sFolderInfoBar.folder.exists()) {
                try {
                    Desktop.getDesktop().browse(SongSync.sFolderInfoBar.folder.toURI());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        openFolderMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = fileChooser.showOpenDialog(frame);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                try {
                    SongSync.sFolderInfoBar.setFolder(selectedFolder);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

     //   settingsMenuItem.addActionListener(e -> {
        //    SettingUI settingsUI = new SettingUI(frame);
           //settingsUI.setVisible(true);
       //});
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}