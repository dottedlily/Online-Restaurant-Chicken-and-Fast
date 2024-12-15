package main.java.main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.main.model.JDBC;

public class CategoryPanel extends JPanel {
    public MainFrame frame;
    public CategoryPanel(MainFrame frame) {
        this.frame = frame;

        setLayout(new GridLayout(2, 4, 10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 50, 0,50));

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.addActionListener(new CategoryButtonListener("Back"));

        List<Category> categories = fetchCategories();
        if (categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
                JButton categoryButton = createCategoryButton(category);
                add(categoryButton);
            }
        }
    }
    private class CategoryButtonListener implements ActionListener {
        private String categoryName;
        public CategoryButtonListener(String name) {
            this.categoryName = name;
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            frame.handleCategoryAction(categoryName);
        }
    }

    private JButton createCategoryButton(Category category) {
        JButton button = new JButton(category.getName());
        button.setBackground(new Color(255, 180, 100, 255));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);

        button.setFont(new Font("San Francisco", Font.BOLD, 25));

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(category.getImagePath()));
        Image image = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(image));

        button.addActionListener(new CategoryButtonListener(category.getName()));

        return button;
    }

    private List<Category> fetchCategories() {
        List<Category> categories = new ArrayList<>();

        String query = "select * from categories;";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("category_name");
                String imagePath = name + ".png";
                categories.add(new Category(name, imagePath));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching categories.");
        }

        return categories;
    }
    }

class Category {
    private String name;
    private String imagePath;

    public Category(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
