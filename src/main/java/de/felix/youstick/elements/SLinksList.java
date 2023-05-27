package de.felix.youstick.elements;

import javax.swing.*;
import java.awt.*;

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
        linksListPanel.setLayout(new BoxLayout(linksListPanel, BoxLayout.Y_AXIS));
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
                JButton downloadButton = new JButton("Download");
                downloadButton.addActionListener(downloadEvent -> System.out.println("Download-Button geklickt für: " + link));

                linkPanel.add(linkLabel, BorderLayout.CENTER);
                linkPanel.add(downloadButton, BorderLayout.EAST);
                linksListPanel.add(linkPanel);
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