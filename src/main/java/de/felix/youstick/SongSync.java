package de.felix.youstick;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.felix.youstick.elements.SLinksList;
import de.felix.youstick.elements.SMenuBar;
import de.felix.youstick.setting.SettingUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongSync {

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        SwingUtilities.invokeLater(SongSync::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("SongSync");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        SMenuBar menuBar = new SMenuBar(frame);
        frame.setJMenuBar(menuBar.getMenuBar());

        SLinksList linksList = new SLinksList();
        JScrollPane linksListScrollPane = linksList.getLinksListScrollPane();

        frame.add(linksList.getTopPanel(), BorderLayout.NORTH);
        frame.add(linksListScrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
