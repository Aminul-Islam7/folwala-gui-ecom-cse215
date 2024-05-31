package com.group8.folwala.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Order implements Serializable {

  private int orderID;
  private User user;
  private ArrayList<OrderItem> orderItems;
  private boolean isDelivered;
  private String paymentMethod;
  private String orderDate;

  public Order(int orderID, User user, ArrayList<OrderItem> orderItems, String paymentMethod) {
    this.orderID = orderID;
    this.user = user;
    this.orderItems = orderItems;
    this.isDelivered = false;
    this.paymentMethod = paymentMethod;
    this.orderDate = new SimpleDateFormat("d MMM yyyy at h:mm a").format(new Date());
  }

  public int getOrderID() {
    return orderID;
  }

  public User getUser() {
    return user;
  }

  public ArrayList<OrderItem> getOrderItems() {
    return orderItems;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void addOrderItem(OrderItem orderItem) {
    for (OrderItem item : orderItems) {
      if (item.getProduct().getProductID() == orderItem.getProduct().getProductID()) {
        item.setQuantity(item.getQuantity() + orderItem.getQuantity());
        return;
      }
    }
    orderItems.add(orderItem);
  }

  public double getTotalPrice() {
    double totalPrice = 0;
    for (OrderItem item : orderItems) {
      totalPrice += item.getProduct().getPrice() * item.getQuantity();
    }
    return totalPrice;
  }

  public boolean isDelivered() {
    return isDelivered;
  }

  public void setDelivered(boolean delivered) {
    isDelivered = delivered;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Order for " + userPhone + ":\n");
    for (OrderItem item : orderItems) {
      sb.append(item.toString()).append("\n");
    }
    sb.append("Total Price: ").append(getTotalPrice());
    return sb.toString();
  }
}
