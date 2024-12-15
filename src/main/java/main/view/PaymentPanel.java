package main.java.main.view;

import main.java.main.controller.CartModel;
import main.java.main.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

public class PaymentPanel extends JPanel {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JRadioButton cashRadioButton;
    private JRadioButton cardRadioButton;
    private ButtonGroup paymentMethodGroup;
    private CartModel cartModel;
    private JTextField paymentMethodField;
    private JLabel totalPriceLabel;
    private JButton payButton;
    private int orderId;
    private MainFrame frame;

    public PaymentPanel(CartModel cartModel, MainFrame frame) {
        this.cartModel = cartModel;
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        nameField.setFont(new Font("San Francisco", Font.BOLD, 16));
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();
        phoneField.setFont(new Font("San Francisco", Font.BOLD, 16));
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        addressField.setFont(new Font("San Francisco", Font.BOLD, 16));
        formPanel.add(addressLabel);
        formPanel.add(addressField);

        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        cashRadioButton = new JRadioButton("Cash");
        cashRadioButton.setFont(new Font("San Francisco", Font.BOLD, 16));
        cardRadioButton = new JRadioButton("Card");
        cardRadioButton.setFont(new Font("San Francisco", Font.BOLD, 16));
        cashRadioButton.setBorder(BorderFactory.createEmptyBorder(20,30,0,30));
        cardRadioButton.setBorder(BorderFactory.createEmptyBorder(20,30,0,30));

        paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(cashRadioButton);
        paymentMethodGroup.add(cardRadioButton);

        cashRadioButton.setSelected(true);

        formPanel.add(paymentMethodLabel);
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new FlowLayout());
        paymentPanel.add(cashRadioButton);
        paymentPanel.add(cardRadioButton);
        paymentPanel.setBackground(Color.WHITE);
        formPanel.add(paymentPanel);

        JButton submitButton = new JButton("Submit");
        submitButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        submitButton.setFont(new Font("San Francisco", Font.BOLD, 16));
        submitButton.setBackground(new Color(204, 204, 204));
        submitButton.setPreferredSize(new Dimension(150, 40));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(50, 205, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(144, 238, 144));
            }
        });

        JPanel submitPanel = new JPanel();
        submitPanel.add(submitButton);
        submitPanel.setBackground(Color.WHITE);
        submitPanel.setBorder(BorderFactory.createEmptyBorder(0,100,100,100));

        add(formPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);
    }

    private void handleSubmit() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String paymentMethod = cashRadioButton.isSelected() ? "Cash" : "Card";

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        processPayment(name, phone, address, paymentMethod);
    }

    private void processPayment(String name, String phone, String address, String paymentMethod) {
        if (cardRadioButton.isSelected()) {
            paymentMethod = "Card";
        }else{
            paymentMethod = "Cash";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String paymentDateString = LocalDateTime.now().format(formatter);

        Timestamp paymentDate = Timestamp.valueOf(paymentDateString);
        double totalAmount = cartModel.getTotalPrice();

        CustomerCRUD.createCustomer(name, phone, address);

        OrderCRUD.createOrder(CustomerCRUD.getLastCustomerId(), totalAmount);
        orderId = OrderCRUD.getLastOrderId();

        System.out.println(orderId);
        for(String[] item : cartModel.cart){
            OrderItemCRUD.createOrderItem(orderId, Integer.parseInt(item[0]), Integer.parseInt(item[4]));
        }
        PaymentCRUD.createPayment(orderId, totalAmount, paymentMethod, paymentDate);
        DeliveryCRUD.createDelivery(orderId, "Pending", Timestamp.valueOf(paymentDateString));

        JOptionPane.showMessageDialog(this, "Payment Processed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        cartModel.cart.clear();
        cartModel.quantity = 0;
        cartModel.totalPrice = 0;
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        paymentMethodGroup.clearSelection();

        this.frame.createThankYouPanel(orderId);
        this.frame.handleCategoryAction("ThankYou");
        DeliveryCRUD.printDeliveries();
        OrderCRUD.printOrders();
        CustomerCRUD.printCustomers();
        OrderItemCRUD.printOrderItems();


    }
}
