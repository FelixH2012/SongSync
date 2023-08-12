package de.felix.songSync;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.felix.songSync.elements.SFolderInfoBar;
import de.felix.songSync.elements.SLinksList;
import de.felix.songSync.elements.SMenuBar;
import de.felix.songSync.util.language.AdaptiveLanguage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SongSync {

    private static final String ICON_LOCATION = "/icon_image.png"; //Just a placeholder don't care

    private static final int INITIAL_WIDTH = 600;
    private static final int INITIAL_HEIGHT = 400;

    public static final boolean debug = false;

    public static SFolderInfoBar sFolderInfoBar;

    public static AdaptiveLanguage adaptiveLanguage;

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        SwingUtilities.invokeLater(SongSync::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("SongSync");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);

        adaptiveLanguage = new AdaptiveLanguage();

        final InputStream imgURL = SongSync.class.getResourceAsStream(ICON_LOCATION);

        if (imgURL != null) {
            try {
                frame.setIconImage(ImageIO.read(imgURL));
            } catch (IOException e) {
                System.out.println("Unable to find icon on icon location: " + ICON_LOCATION);
            }
        } else {
            System.out.println("Unable to find url path ");
        }
        final SMenuBar menuBar = new SMenuBar(frame);
        frame.setJMenuBar(menuBar.getMenuBar());

        final SLinksList linksList = new SLinksList();
        final JScrollPane linksListScrollPane = linksList.getLinksListScrollPane();

        sFolderInfoBar = new SFolderInfoBar();

        frame.add(linksList.getTopPanel(), BorderLayout.NORTH);
        frame.add(linksListScrollPane, BorderLayout.CENTER);

        frame.add(sFolderInfoBar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
