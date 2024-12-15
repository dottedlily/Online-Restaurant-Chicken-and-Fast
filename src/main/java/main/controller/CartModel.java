package main.java.main.controller;

import java.util.ArrayList;
import java.util.List;

public class CartModel
{
    public int quantity;
    public List<String[]> cart;
    public double totalPrice = 0;
    public CartModel(){
        this.cart = new ArrayList<>();
    }

    public void addItem(String[] item, String imagePath) {
        boolean itemExists = false;

        for (String[] cartItem : cart) {
            if (cartItem[0].equals(item[0])) {
                itemExists = true;

                int currentQuantity = Integer.parseInt(cartItem[4]);
                int newQuantity = currentQuantity + 1;
                cartItem[4] = String.valueOf(newQuantity);

                break;
            }
        }

        if (!itemExists) {
            String[] newItem = new String[item.length + 2];
            System.arraycopy(item, 0, newItem, 0, item.length);
            newItem[item.length] = "1";
            newItem[item.length + 1] = imagePath;
            cart.add(newItem);
        }
        addProductPrice(item[2]);
        updateCartQuantity();
    }

    public void updateCartQuantity(){
        quantity ++;
    }

    public void addProductPrice(String price){
        totalPrice += Double.parseDouble(price);
    }

    public int getQuantity(){
        return quantity;
    }

    public double getTotalPrice(){
        return totalPrice;
    }
}

