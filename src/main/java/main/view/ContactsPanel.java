package main.java.main.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

class ContactsPanel extends JPanel {
    public ContactsPanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Contact our CEOS!", SwingConstants.CENTER);
        headerLabel.setFont(new Font("San Francisco", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 10, 0));
        headerLabel.setForeground(Color.DARK_GRAY);

        JPanel operatorsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        operatorsPanel.setBorder(BorderFactory.createEmptyBorder(70, 70, 70, 70));
        operatorsPanel.setBackground(Color.WHITE);

        // Add Aiana's card and Nura's card
        JPanel aianaPanel = createOperatorPanel("Aiana", "aiana.png", "aiana.kutmanova@alatoo.edu.kg");
        JPanel nuraPanel = createOperatorPanel("Nura", "elnura.png", "elnura.orozmamatova@alatoo.edu.kg");
        operatorsPanel.add(aianaPanel);
        operatorsPanel.add(nuraPanel);

        add(headerLabel, BorderLayout.NORTH);
        add(operatorsPanel, BorderLayout.CENTER);
    }

    private JPanel createOperatorPanel(String name, String imageName, String email) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setLayout(new BorderLayout());

        // Load profile image
        JLabel profileLabel = new JLabel();
        profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            URL imageUrl = getClass().getClassLoader().getResource(imageName);
            if (imageUrl != null) {
                ImageIcon imageIcon = new ImageIcon(imageUrl);
                if(Objects.equals(name, "Aiana")) {
                    Image image = imageIcon.getImage().getScaledInstance(250, 260, Image.SCALE_SMOOTH);
                    profileLabel.setIcon(new ImageIcon(image));
                }else{
                    Image image = imageIcon.getImage().getScaledInstance(250, 330, Image.SCALE_SMOOTH);
                    profileLabel.setIcon(new ImageIcon(image));
                }

            } else {
                profileLabel.setText("No Image");
            }
        } catch (Exception e) {
            profileLabel.setText("No Image");
        }

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("San Francisco", Font.BOLD, 24));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel emailLabel = new JLabel(email);
        emailLabel.setFont(new Font("San Francisco", Font.PLAIN, 14));
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setForeground(Color.DARK_GRAY);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(nameLabel);
        infoPanel.add(emailLabel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(profileLabel, BorderLayout.NORTH);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }
}
