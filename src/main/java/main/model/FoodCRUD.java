package main.java.main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodCRUD {

    public static String[] createFood(String foodName, int categoryId, int price) {
        String query = "insert into foods (food_name, category_id, price) values (?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, foodName);
            statement.setInt(2, categoryId);
            statement.setInt(3, price);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return new String[]{foodName, "added successfully"};
            } else {
                return new String[]{"failed to add food item", foodName};
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"error", e.getMessage()};
        }
    }

    public static List<String[]> getAllFoods() {
        String query = "select * from foods";
        List<String[]> foodList = new ArrayList<>();

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String[] food = {
                        String.valueOf(resultSet.getInt("food_id")),
                        resultSet.getString("food_name"),
                        String.valueOf(resultSet.getInt("category_id")),
                        String.valueOf(resultSet.getInt("price"))
                };
                foodList.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    public static String[] updateFood(int foodId, String newName, int newCategoryId, int newPrice) {
        String query = "update foods set food_name = ?, category_id = ?, price = ? where food_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newName);
            statement.setInt(2, newCategoryId);
            statement.setInt(3, newPrice);
            statement.setInt(4, foodId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return new String[]{"food item updated successfully"};
            } else {
                return new String[]{"no food item found with the provided id"};
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"error", e.getMessage()};
        }
    }

    public static String[] deleteFood(int foodId) {
        String query = "delete from foods where food_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, foodId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return new String[]{"food item deleted successfully"};
            } else {
                return new String[]{"no food item found with the provided id"};
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"error", e.getMessage()};
        }
    }

    public static String[] getFoodById(int foodId) {
        String query = "select * from foods where food_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, foodId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new String[]{
                        String.valueOf(resultSet.getInt("food_id")),
                        resultSet.getString("food_name"),
                        String.valueOf(resultSet.getInt("category_id")),
                        String.valueOf(resultSet.getInt("price"))
                };
            } else {
                return new String[]{"no food item found with the provided id"};
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"error", e.getMessage()};
        }
    }
}
