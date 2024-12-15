package main.java.main.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryCRUD {

    public static void createDelivery(int orderId, String status, Timestamp date) {
        String query = "insert into deliveries (order_id, status, date) values (?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setString(2, status);
            statement.setTimestamp(3, date);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllDeliveries() {
        List<String[]> deliveries = new ArrayList<>();
        String query = "select * from deliveries";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                deliveries.add(new String[]{
                        String.valueOf(resultSet.getInt("delivery_id")),
                        String.valueOf(resultSet.getInt("order_id")),
                        resultSet.getString("status"),
                        resultSet.getString("date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    public static void updateDelivery(int id, int orderId, String status, Timestamp date) {
        String query = "update deliveries set order_id = ?, status = ?, date = ? where delivery_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setString(2, status);
            statement.setTimestamp(3, date);
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printDeliveries() {
        String query = "select * from deliveries";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Delivery Records:");
            System.out.println("------------------------------------------------------");
            System.out.printf("%-10s %-10s %-15s %-20s\n", "Delivery ID", "Order ID", "Status", "Date");

            while (resultSet.next()) {
                int deliveryId = resultSet.getInt("delivery_id");
                int orderId = resultSet.getInt("order_id");
                String status = resultSet.getString("status");
                String date = String.valueOf(resultSet.getTimestamp("date"));

                System.out.printf("%-10d %-10d %-15s %-20s\n", deliveryId, orderId, status, date);
            }

            System.out.println("------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
