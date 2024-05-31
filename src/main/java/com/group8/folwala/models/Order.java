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
  private String shippingAddress;

  public Order(int orderID, User user, ArrayList<OrderItem> orderItems, String paymentMethod, String shippingAddress) {
    this.orderID = orderID;
    this.user = user;
    this.orderItems = orderItems;
    this.isDelivered = false;
    this.paymentMethod = paymentMethod;
    Date date = new Date();
    this.orderDate = new SimpleDateFormat("d MMM yyyy").format(date)
        + " at " + new SimpleDateFormat("h:mm a").format(date);
    this.shippingAddress = shippingAddress;
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

  public String getShippingAddress() {
    return shippingAddress;
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
    return "Order{" +
        "orderID=" + orderID +
        ", user=" + user +
        ", orderItems=" + orderItems +
        ", isDelivered=" + isDelivered +
        ", paymentMethod='" + paymentMethod + '\'' +
        ", orderDate='" + orderDate + '\'' +
        '}';
  }
}
