package de.felix.songSync.setting;

import javax.swing.*;
import java.awt.*;

public class SettingUI extends JDialog {

    public SettingUI(JFrame parent) {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        setTitle("Settings");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);

        final JPanel settingsPanel = new JPanel();
        final JLabel settingsLabel = new JLabel("General");
        settingsPanel.add(settingsLabel);

        final JPanel settingsSubPanel = new JPanel();
        settingsSubPanel.setLayout(new BoxLayout(settingsSubPanel, BoxLayout.Y_AXIS));

        final JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JCheckBox songsCheckbox = new JCheckBox("Mix songs to same loudness");
        checkBoxPanel.add(songsCheckbox);
        settingsSubPanel.add(checkBoxPanel);

        tabbedPane.addTab("General", settingsPanel);
        tabbedPane.addTab("Songs", settingsSubPanel);
    }
}
