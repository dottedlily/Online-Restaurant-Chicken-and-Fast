package main.java.main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderCRUD {
    public static void createOrder(int customerId, double amount) {
        System.out.println("Order created successfully for customer ID: " + customerId);

        String query = "insert into orders (customer_id, amount) values (?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            statement.setDouble(2, amount);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBC.refreshConnection();
    }


    public static List<String[]> getAllOrders() {
        List<String[]> orders = new ArrayList<>();
        String query = "select * from orders";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                orders.add(new String[]{
                        String.valueOf(resultSet.getInt("order_id")),
                        String.valueOf(resultSet.getInt("customer_id")),
                        String.valueOf(resultSet.getDouble("amount")),
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static void updateOrder(int id, int customerId, int foodId, int quantity) {
        String query = "update orders set customer_id = ?, food_id = ?, quantity = ? where order_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            statement.setInt(2, foodId);
            statement.setInt(3, quantity);
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrder(int id) {
        String query = "delete from orders where order_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getLastOrderId() {
        String query = "select max(order_id) from orders";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void printOrders() {
        String query = "select * from orders";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Order Records:");
            System.out.println("------------------------------------------------------");
            System.out.printf("%-10s %-15s %-10s\n", "Order ID", "Customer ID", "Amount");

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                double amount = resultSet.getDouble("amount");

                System.out.printf("%-10d %-15d %-10.2f\n", orderId, customerId, amount);
            }

            System.out.println("------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
