package de.felix.youstick.elements;

import de.felix.youstick.setting.SettingUI;

import javax.swing.*;
import java.io.File;

public class SMenuBar {
    private JMenuBar menuBar;

    public SMenuBar(JFrame frame) {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu settingsMenu = new JMenu("Settings");
        menuBar.add(settingsMenu);

        JMenuItem settingsMenuItem = new JMenuItem("Open settings");
        settingsMenu.add(settingsMenuItem);

        JMenuItem openFolderMenuItem = new JMenuItem("Select song folder");
        fileMenu.add(openFolderMenuItem);
        openFolderMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = fileChooser.showOpenDialog(frame);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                System.out.println("Ausgewählter Ordner: " + selectedFolder.getAbsolutePath());
            }
        });

        settingsMenuItem.addActionListener(e -> {
            SettingUI settingsUI = new SettingUI(frame);
            settingsUI.setVisible(true);
        });
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}