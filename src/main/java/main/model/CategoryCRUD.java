package main.java.main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryCRUD {

    public static void createCategory(String categoryName) {
        String query = "insert into categories (category_name) values (?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, categoryName);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllCategories() {
        List<String[]> categories = new ArrayList<>();
        String query = "select * from categories";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                categories.add(new String[]{
                        String.valueOf(resultSet.getInt("category_id")),
                        resultSet.getString("category_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public static void updateCategory(int id, String categoryName) {
        String query = "update categories set category_name = ? where category_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, categoryName);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCategory(int id) {
        String query = "delete from categories where category_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String[]> getItemsByCategory(String categoryName) {
        List<String[]> items = new ArrayList<>();

        String query = "SELECT f.food_id, f.food_name, f.price, c.category_name FROM foods f " +
                "JOIN categories c ON f.category_id = c.category_id " +
                "WHERE c.category_name = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                items.add(new String[]{
                        String.valueOf(resultSet.getInt("food_id")),
                        resultSet.getString("food_name"),
                        String.valueOf(resultSet.getInt("price")),
                        resultSet.getString("category_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

}
