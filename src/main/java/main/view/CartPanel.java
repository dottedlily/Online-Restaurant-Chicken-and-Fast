package main.java.main.view;

import main.java.main.controller.CartModel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class CartPanel extends JPanel {
    private CartModel cartModel;
    private JPanel itemListPanel;
    private JLabel totalQuantityLabel;
    private JLabel totalPriceLabel;
    private JButton payButton;
    private List<String[]> cartItems;
    private MainFrame frame;

    public CartPanel(CartModel cartModel, MainFrame frame) {
        this.cartModel = cartModel;
        this.frame = frame;
        List<String[]> cartItems = cartModel.cart;
        this.cartItems = cartItems;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel columnNamePanel = new JPanel();
        columnNamePanel.setLayout(new GridLayout(1, 5));
        columnNamePanel.setPreferredSize(new Dimension(0, 100));
        columnNamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        columnNamePanel.setMinimumSize(new Dimension(0, 100));
        columnNamePanel.setBackground(Color.WHITE);
        columnNamePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 30));
        JLabel columnImageLabel = new JLabel("Image");
        columnImageLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        columnNamePanel.add(columnImageLabel);
        JLabel columnNameLabel = new JLabel("Name");
        columnNameLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        columnNamePanel.add(columnNameLabel);
        JLabel columnPriceLabel = new JLabel("Price KGS");
        columnPriceLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        columnNamePanel.add(columnPriceLabel);
        JLabel columnQuantityLabel = new JLabel("Quantity");
        columnQuantityLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        columnNamePanel.add(columnQuantityLabel);
        JLabel columnTotalPriceLabel = new JLabel("Total KGS");
        columnTotalPriceLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        columnNamePanel.add(columnTotalPriceLabel);

        itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        itemListPanel.setBackground(Color.WHITE);
        itemListPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        JPanel summaryPanel = new JPanel(new GridLayout(3, 1));
        summaryPanel.setBackground(Color.WHITE);
        totalQuantityLabel = new JLabel("Total Quantity: 0");
        totalQuantityLabel.setFont(new Font("San Francisco", Font.BOLD, 16));
        totalQuantityLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        totalPriceLabel = new JLabel("Total Price: KGS 0.00");
        totalPriceLabel.setFont(new Font("San Francisco", Font.BOLD, 16));
        totalPriceLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        payButton = new JButton("Proceed to checkout");
        payButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        payButton.setFont(new Font("San Francisco", Font.BOLD, 16));

        payButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100), BorderFactory.createLineBorder(Color.LIGHT_GRAY)));

        payButton.setPreferredSize(new Dimension(200, 100));
        payButton.setMinimumSize(new Dimension(200, 100));
        payButton.setMaximumSize(new Dimension(200, 100));
        payButton.setBackground(Color.LIGHT_GRAY);

        payButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(cartItems.size() != 0) {
                    payButton.setBackground(new Color(50, 205, 50));
                    payButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100), BorderFactory.createLineBorder(new Color(50, 205, 50))));

                }else {
                    payButton.setBackground(Color.GRAY);
                    payButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100), BorderFactory.createLineBorder(Color.GRAY)));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(cartItems.size() != 0) {
                    payButton.setBackground(new Color(144, 238, 144));
                    payButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100), BorderFactory.createLineBorder(new Color(144, 238, 144))));
                }else{
                    payButton.setBackground(Color.LIGHT_GRAY);
                    payButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100), BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
                }
            }
        });
        payButton.setBorderPainted(true);

        summaryPanel.add(totalQuantityLabel);
        summaryPanel.add(totalPriceLabel);
        summaryPanel.add(payButton);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 100, 30));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.add(columnNamePanel, BorderLayout.NORTH);
        wrapperPanel.add(itemListPanel, BorderLayout.CENTER);
        wrapperPanel.add(summaryPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        updateCartDisplay();

        payButton.addActionListener(e -> processPayment());
    }

    public void updateCartDisplay() {
        itemListPanel.removeAll();


        List<String[]> cartItems = cartModel.cart;
        double totalPrice = 0;

        if(cartItems.size() == 0) {
            payButton.setBackground(Color.LIGHT_GRAY);
            payButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100), BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
        }else {
            payButton.setBackground(new Color(144, 238, 144));
            payButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100), BorderFactory.createLineBorder(new Color(144, 238, 144))));
        }

        for (String[] item : cartItems) {
            String name = item[1];
            double price = Double.parseDouble(item[2]);
            int quantity = Integer.parseInt(item[4]);
            totalPrice += price * quantity;

            // Item panel for each cart item
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new GridLayout(1, 5));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            itemPanel.setPreferredSize(new Dimension(0, 100));
            itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            itemPanel.setMinimumSize(new Dimension(0, 100));
            itemPanel.setBackground(Color.WHITE);


            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("San Francisco", Font.BOLD, 16));
            nameLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            JLabel priceLabel = new JLabel("" + price);
            priceLabel.setFont(new Font("San Francisco", Font.PLAIN, 14));
            priceLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            JLabel quantityLabel = new JLabel("" + quantity);
            quantityLabel.setFont(new Font("San Francisco", Font.PLAIN, 14));
            quantityLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            JLabel totalPriceLabel = new JLabel("" + quantity * price);
            totalPriceLabel.setFont(new Font("San Francisco", Font.BOLD, 16));
            totalPriceLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));


            URL imageUrl = getClass().getClassLoader().getResource(item[5]);
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

            itemPanel.add(imageLabel);
            itemPanel.add(nameLabel);
            itemPanel.add(priceLabel);
            itemPanel.add(quantityLabel);
            itemPanel.add(totalPriceLabel);

            itemListPanel.add(itemPanel);
        }

        totalPriceLabel.setText("Total: KGS  " + String.format("%.2f", totalPrice));
        totalQuantityLabel.setText("Total Quantity: " + cartModel.quantity);

        revalidate();
        repaint();
    }


    // Handle Payment Process
    private void processPayment() {
        if (cartModel.cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            frame.handleCategoryAction("Payment");


        }
    }
}
