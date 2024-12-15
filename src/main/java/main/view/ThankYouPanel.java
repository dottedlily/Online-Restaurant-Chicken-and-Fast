package main.java.main.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ThankYouPanel extends JPanel {
    private int orderId;

    public ThankYouPanel(int orderId) {
        this.orderId = orderId;
        setLayout(new BorderLayout());
        JPanel thankYouMessagePanel = new JPanel();
        thankYouMessagePanel.setBackground(Color.WHITE);
        JLabel thankYouMessage = new JLabel("Thank you for your purchase!", JLabel.CENTER);
        thankYouMessage.setFont(new Font("Arial", Font.BOLD, 34));
        thankYouMessage.setBackground(Color.WHITE);
        thankYouMessage.setForeground(new Color(255, 204, 0));  // Yellow text color
        thankYouMessage.setBorder(BorderFactory.createEmptyBorder(30,30,90,30));
        thankYouMessagePanel.add(thankYouMessage);
        add(thankYouMessagePanel, BorderLayout.NORTH);




        URL imageUrl = getClass().getClassLoader().getResource("logo.png");
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        Image image = imageIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(imageLabel);
        imagePanel.setPreferredSize(new Dimension(470, 400));
        add(imagePanel, BorderLayout.CENTER);



        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.setBackground(Color.WHITE);

        JLabel orderLabel = new JLabel("Your Order ID: " + orderId, JLabel.CENTER);
        orderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        orderLabel.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);

        JLabel footerLabel = new JLabel("We appreciate your business!", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(0,30,100,30));

        footerPanel.add(footerLabel);
        infoPanel.add(orderLabel);
        infoPanel.add(footerPanel);

        add(infoPanel, BorderLayout.SOUTH);
    }

    public void setDetails(int orderId) {
        this.orderId = orderId;
    }
}
