package com.group8.folwala.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {

  private String userPhone;
  private ArrayList<CartItem> cartItems;

  public Cart(String userPhone) {
    this.userPhone = userPhone;
    this.cartItems = new ArrayList<>();
  }

  public Cart(String userPhone, ArrayList<CartItem> cartItems) {
    this.userPhone = userPhone;
    this.cartItems = cartItems;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public ArrayList<CartItem> getCartItems() {
    return cartItems;
  }

  public void addCartItem(CartItem cartItem) {
    for (CartItem item : cartItems) {
      if (item.getProduct().getProductID() == cartItem.getProduct().getProductID()) {
        item.setQuantity(item.getQuantity() + cartItem.getQuantity());
        return;
      }
    }
    cartItems.add(cartItem);
  }

  public double getTotalPrice() {
    double totalPrice = 0;
    for (CartItem item : cartItems) {
      totalPrice += item.getProduct().getPrice() * item.getQuantity();
    }
    return totalPrice;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Cart for " + userPhone + ":\n");
    for (CartItem item : cartItems) {
      sb.append(item.toString()).append("\n");
    }
    sb.append("Total Price: ").append(getTotalPrice());
    return sb.toString();
  }
}
