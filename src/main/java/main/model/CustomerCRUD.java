package main.java.main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerCRUD {

    public static void createCustomer(String name, String phone, String address) {
        String query = "insert into customers (name, phone, address) values (?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllCustomers() {
        List<String[]> customers = new ArrayList<>();
        String query = "select * from customers";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                customers.add(new String[]{
                        String.valueOf(resultSet.getInt("customer_id")),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static void updateCustomer(int id, String name, String phone, String address) {
        String query = "update customers set name = ?, phone = ?, address = ? where customer_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(int id) {
        String query = "delete from customers where customer_id = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getLastCustomerId() {
        String query = "select max(customer_id) from customers";

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
    public static void printCustomers() {
        String query = "select * from customers";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Customer Records:");
            System.out.println("------------------------------------------------------");
            System.out.printf("%-10s %-20s %-15s\n", "Customer ID", "Name", "Phone");

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                System.out.printf("%-10d %-20s %-15s\n", customerId, name, phone);
            }

            System.out.println("------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
