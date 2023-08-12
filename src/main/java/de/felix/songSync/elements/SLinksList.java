package de.felix.songSync.elements;

import de.felix.songSync.SongSync;
import de.felix.songSync.downloader.downloadables.YouTube;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SLinksList {
    private JTextArea linksBar;
    private JScrollPane linksBarScrollPane;
    private JPanel topPanel;
    private JPanel linksListPanel;
    private JScrollPane linksListScrollPane;

    public SLinksList() {
        createLinksBar();
        createLinksList();
        createTopPanel();
    }

    private void createLinksBar() {
        linksBar = new JTextArea();
        linksBar.setEditable(true);
        linksBarScrollPane = new JScrollPane(linksBar);
    }

    private void createLinksList() {
        linksListPanel = new JPanel();
        linksListPanel.setLayout(new GridBagLayout());
        linksListScrollPane = new JScrollPane(linksListPanel);
    }

    private void createTopPanel() {
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(linksBarScrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("➕");
        addButton.addActionListener(e -> {
            String link = linksBar.getText().trim();
            if (!link.isEmpty()) {
                JPanel linkPanel = new JPanel(new BorderLayout());
                JLabel linkLabel = new JLabel(link) {
                    @Override
                    public Dimension getPreferredSize() {
                        Dimension preferredSize = super.getPreferredSize();
                        int maxWidth = 200;
                        int maxHeight = 30;
                        int width = Math.min(preferredSize.width, maxWidth);
                        int height = Math.min(preferredSize.height, maxHeight);
                        return new Dimension(width, height);
                    }
                };
                linkLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                JButton downloadButton = new JButton(SongSync.adaptiveLanguage.getLanguageManager().getMessage("download"));
                downloadButton.addActionListener(downloadEvent -> {

                    final YouTube youTube = new YouTube(link, SongSync.sFolderInfoBar.folder);
                    youTube.download();

                    try {
                        SongSync.sFolderInfoBar.refresh(SongSync.sFolderInfoBar.folder);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                });

                linkPanel.add(linkLabel, BorderLayout.CENTER);
                linkPanel.add(downloadButton, BorderLayout.EAST);
                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
                gridBagConstraints.anchor = GridBagConstraints.NORTH;
                gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
                gridBagConstraints.weightx = 1;
                gridBagConstraints.weighty = 0;
                gridBagConstraints.insets = new Insets(0, 0, 5, 0);

                linksListPanel.add(linkPanel, gridBagConstraints);
                linksListPanel.revalidate();
                linksListPanel.repaint();
            }
            linksBar.setText("");
        });

        topPanel.add(addButton, BorderLayout.EAST);
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JScrollPane getLinksListScrollPane() {
        return linksListScrollPane;
    }
}