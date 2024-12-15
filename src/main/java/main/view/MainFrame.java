package main.java.main.view;

import main.java.main.controller.CartModel;
import main.java.main.model.CategoryCRUD;
import main.java.main.model.PaymentCRUD;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class MainFrame extends JFrame {
    private final CartModel cartModel;
    private final JPanel switchPanel;
    private final CartPanel cartPanel;
    private ThankYouPanel currentThankYouPanel;

    public MainFrame(CartModel cartModel) {
        this.cartModel = cartModel;
        this.cartPanel = new CartPanel(cartModel, this);
        PaymentPanel paymentPanel = new PaymentPanel(cartModel, this);
        this.currentThankYouPanel = new ThankYouPanel(-10000);
        PaymentCRUD.deleteAllPayments();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chicken and fast");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        HeaderPanel headerPanel = new HeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        JPanel leftPanelInCenter = createImagePanel("menu_board.png");
        JPanel rightPanelInCenter = createImagePanel("combo_board.png");

        switchPanel = new JPanel(new CardLayout());
        List<String> categories = List.of("Beverages", "Desserts", "Burgers", "Buckets", "Chicken", "Side Dishes", "Combos", "Twisters");
        initializePanels(categories);

        CategoryPanel categoryPanel = new CategoryPanel(this);
        switchPanel.add(categoryPanel, "PanelCategory");

        switchPanel.add(cartPanel, "PanelCart");
        switchPanel.add(paymentPanel, "PanelPayment");

        ContactsPanel contactsPanel = new ContactsPanel();
        switchPanel.add(contactsPanel, "PanelContacts");



        CardLayout cl = (CardLayout) switchPanel.getLayout();
        cl.show(this.switchPanel, "PanelCategory");

        centerPanel.add(leftPanelInCenter, BorderLayout.WEST);
        centerPanel.add(switchPanel, BorderLayout.CENTER);
        centerPanel.add(rightPanelInCenter, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void initializePanels(List<String> categoryNames) {
        for (String categoryName : categoryNames) {
            JScrollPane categoryPanel = createPanelForCategory(categoryName);
            this.switchPanel.add(categoryPanel, "Panel" + categoryName);
        }
    }

    public void handleCategoryAction(String categoryName) {
        if (!categoryName.equals("ThankYou")) {
            this.switchPanel.remove(currentThankYouPanel);
        }
        CardLayout cl = (CardLayout) this.switchPanel.getLayout();
        cl.show(switchPanel, "Panel" + categoryName);
    }

    private JScrollPane createPanelForCategory(String categoryName) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JButton backButton = new JButton("Continue Shopping");

        backButton.setFont(new Font("San Francisco", Font.BOLD,20));
        backButton.setPreferredSize(new Dimension(panel.getWidth(), 80));
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 70, 0, 70),
                BorderFactory.createLineBorder(Color.BLACK, 1)
        ));

        backButton.addActionListener(e -> {
            handleCategoryAction("Category");
        });
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridBagLayout());
        categoryPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        List<String[]> items = CategoryCRUD.getItemsByCategory(categoryName);

        for (String[] item : items) {
            String itemName = item[1];
            String itemPrice = item[2];

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new GridBagLayout());
            itemPanel.setPreferredSize(new Dimension(200, 200));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setMaximumSize(new Dimension(200, 200));
            itemPanel.setMinimumSize(new Dimension(200, 200));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            itemPanel.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            String picName = item[3] + "Icon.png";
            try {
                URL imageUrl = getClass().getClassLoader().getResource(picName);
                if (imageUrl != null) {
                    ImageIcon imageIcon = new ImageIcon(imageUrl);
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                } else {
                    imageLabel.setText("No Image");
                }
            } catch (Exception e) {
                imageLabel.setText("No Image");
            }

            JLabel nameLabel = new JLabel(itemName);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel priceLabel = new JLabel("KGS " + itemPrice);
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JButton cartButton = new JButton("Add to cart");
            cartButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            cartButton.setFont(new Font("Arial", Font.PLAIN, 12));
            cartButton.setBackground(Color.WHITE);
            cartButton.setFocusPainted(false);
            cartButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            cartButton.setBackground( new Color(240, 240, 240));

            final boolean[] isClicked = {false};

            cartButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!isClicked[0]) {
                        cartButton.setBackground(new Color(102, 204, 102));
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!isClicked[0]) {
                        cartButton.setBackground(new Color(240, 240, 240));
                    }
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    cartButton.setBackground(new Color(0, 153, 76));
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    if (!isClicked[0]) {
                        cartButton.setBackground(new Color(102, 204, 102));
                    }
                }
            });

            cartButton.addActionListener(e -> {
                cartModel.addItem(item, picName);
                cartPanel.updateCartDisplay();
                if (!isClicked[0]) {
                    cartButton.setBackground(new Color(0, 153, 76));
                    isClicked[0] = true;
                } else {
                    cartButton.setBackground(new Color(240, 240, 240));
                    isClicked[0] = false;
                }
            });

            GridBagConstraints gbc2 = new GridBagConstraints();

            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.gridwidth = 2;
            gbc2.fill = GridBagConstraints.HORIZONTAL;
            itemPanel.add(imageLabel, gbc2);

            gbc2.gridy = 1;
            itemPanel.add(nameLabel, gbc2);

            gbc2.gridy = 2;
            gbc2.gridwidth = 1;
            itemPanel.add(priceLabel, gbc2);

            gbc2.gridx = 1;
            gbc2.anchor = GridBagConstraints.EAST;
            itemPanel.add(cartButton, gbc2);

            categoryPanel.add(itemPanel, gbc);

            gbc.gridx++;
            if (gbc.gridx > 2) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }

        panel.add(backButton, BorderLayout.NORTH);
        panel.add(categoryPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(panel);

        return scrollPane;

    }

    public void createThankYouPanel(int orderId) {
        this.cartPanel.updateCartDisplay();
        currentThankYouPanel = new ThankYouPanel(orderId);
        this.switchPanel.add(currentThankYouPanel, "PanelThankYou");
        handleCategoryAction("ThankYou");
    }

    public void deleteThankYouPanel() {
        this.switchPanel.remove(currentThankYouPanel);
        handleCategoryAction("Category");
    }

    private JPanel createImagePanel(String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            Image image = imageIcon.getImage().getScaledInstance(250, 500, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            panel.add(imageLabel, BorderLayout.CENTER);
            panel.setBackground(Color.ORANGE);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            JLabel errorLabel = new JLabel("Image not found");
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        return panel;
    }


    class HeaderPanel extends JPanel {
        public HeaderPanel() {
            setPreferredSize(new Dimension(this.getWidth(), 100));
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            JLabel iconLabel = new JLabel();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
            Image image = icon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);

            iconLabel.setIcon(icon);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
            add(iconLabel, BorderLayout.WEST);

            JPanel rightPanel = new JPanel();
            rightPanel.setOpaque(false);
            rightPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));
            rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

            JButton categoriesButton = createButton("menu.png", "Categories");
            categoriesButton.addActionListener(e -> {
                handleCategoryAction("Category");
            });
            JButton contactButton = createButton("phone.png", "+996 (500) 20 30 40");
            contactButton.addActionListener(e -> {
                handleCategoryAction("Contacts");
            });
            JButton cartButton = createButton("carts-2.png", "Cart");
            cartButton.addActionListener(e -> {
                handleCategoryAction("Cart");
            });

            rightPanel.add(categoriesButton);
            rightPanel.add(contactButton);
            rightPanel.add(cartButton);

            add(rightPanel, BorderLayout.EAST);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int shadowHeight = 10;

            GradientPaint gradientPaint = new GradientPaint(
                    0, getHeight() - shadowHeight, new Color(0, 0, 0, 0.3f),
                    0, getHeight(), new Color(0, 0, 0, 0)
            );

            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, getHeight() - shadowHeight, getWidth(), shadowHeight);
        }

        private JButton createButton(String iconName, String text) {
            try {

                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconName));

                Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);
                JButton button = new JButton(text, icon);
                button.setFont(new Font("San Francisco", Font.BOLD, 13));
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
                button.setBackground(Color.WHITE);
                button.setOpaque(true);

                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        button.setBackground(new Color(255, 180, 100, 255));
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        button.setBackground(Color.WHITE);
                    }
                });
                return button;
            } catch (Exception e) {
                System.err.println("Failed to load button icon: " + e.getMessage());
                return new JButton(text);
            }
        }
    }




}




