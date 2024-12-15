package main.java.main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemCRUD {

    public static void createOrderItem(int orderId, int foodId, int quantity) {
        String query = "insert into order_items (order_id, food_id, quantity) values (?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setInt(2, foodId);
            statement.setInt(3, quantity);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllOrderItems() {
        List<String[]> orderItems = new ArrayList<>();
        String query = "select * from order_items";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                orderItems.add(new String[]{
                        String.valueOf(resultSet.getInt("order_item_id")),
                        String.valueOf(resultSet.getInt("order_id")),
                        String.valueOf(resultSet.getInt("food_id")),
                        String.valueOf(resultSet.getInt("quantity"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public static void updateOrderItem(int id, int orderId, int foodId, int quantity) {
        String query = "update order_items set order_id = ?, food_id = ?, quantity = ? where order_item_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setInt(2, foodId);
            statement.setInt(3, quantity);
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrderItem(int id) {
        String query = "delete from order_items where order_item_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printOrderItems() {
        String query = "select * from order_items";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Order Item Records:");
            System.out.println("------------------------------------------------------");
            System.out.printf("%-13s %-11s %-10s %-10s\n", "Order Item ID", "Order ID", "Product ID", "Quantity");

            while (resultSet.next()) {
                int orderItemId = resultSet.getInt("order_item_id");
                int orderId = resultSet.getInt("order_id");
                int productId = resultSet.getInt("food_id");
                int quantity = resultSet.getInt("quantity");

                System.out.printf("%-10d %-10d %-10d %-10d\n", orderItemId, orderId, productId, quantity);
            }

            System.out.println("------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
