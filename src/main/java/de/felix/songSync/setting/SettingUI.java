package de.felix.songSync.setting;

import de.felix.songSync.data.ChangeLoudness;

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

        JPanel settingsPanel = new JPanel();
        JLabel settingsLabel = new JLabel("General");
        settingsPanel.add(settingsLabel);

        JPanel settingsSubPanel = new JPanel();
        settingsSubPanel.setLayout(new BoxLayout(settingsSubPanel, BoxLayout.Y_AXIS));

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox songsCheckbox = new JCheckBox("Mix songs to same loudness");
        checkBoxPanel.add(songsCheckbox);
        settingsSubPanel.add(checkBoxPanel);

        songsCheckbox.addActionListener(e -> ChangeLoudness.canChangeLoudness = songsCheckbox.isSelected());

        tabbedPane.addTab("General", settingsPanel);
        tabbedPane.addTab("Songs", settingsSubPanel);
    }
}
