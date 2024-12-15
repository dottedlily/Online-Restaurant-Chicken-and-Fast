package main.java.main.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentCRUD {
    public static void createPayment(int orderId, double amount, String paymentMethod, Timestamp paymentDate) {
        String query = "insert into payments (order_id, amount, payment_method, payment_date) values (?, ?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentMethod);
            statement.setTimestamp(4, paymentDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllPayments() {
        List<String[]> payments = new ArrayList<>();
        String query = "select * from payments";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                payments.add(new String[]{
                        String.valueOf(resultSet.getInt("payment_id")),
                        String.valueOf(resultSet.getInt("order_id")),
                        String.valueOf(resultSet.getDouble("amount")),
                        String.valueOf(resultSet.getString("payment_method")),
                        resultSet.getString("payment_date")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public static void updatePayment(int id, int orderId, double amount, String paymentDate, String paymentMethod ) {
        String query = "update payments set order_id = ?, amount = ?, payment_method = ?, payment_date = ? where payment_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentMethod);
            statement.setString(4, paymentDate);
            statement.setInt(5, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePayment(int id) {
        String query = "delete from payments where payment_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllPayments() {
        String query = "delete from payments";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
