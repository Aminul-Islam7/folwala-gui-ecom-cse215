package com.group8.folwala.services;

import com.group8.folwala.models.CartItem;
import com.group8.folwala.models.Order;
import com.group8.folwala.models.OrderItem;
import com.group8.folwala.models.User;

import java.io.*;
import java.util.ArrayList;

public class OrderService {

  private static final String ORDER_FILE_PATH = "src/main/resources/data/orders.dat";

  public static ArrayList<Order> loadOrders() {
    ArrayList<Order> orders = new ArrayList<>();
    File file = new File(ORDER_FILE_PATH);
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        orders = (ArrayList<Order>) ois.readObject();
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("No orders found or failed to load orders: " + e.getMessage());
        e.printStackTrace();
      }
    }
    return orders;
  }

  public static void saveOrders(ArrayList<Order> orders) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER_FILE_PATH))) {
      oos.writeObject(orders);
    } catch (IOException e) {
      System.out.println("Failed to save orders: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void placeOrder(User user, ArrayList<OrderItem> orderItems, String paymentMethod,
      String shippingAddress) {
    Order order = new Order(generateOrderID(), user, orderItems, paymentMethod, shippingAddress);
    ArrayList<Order> orders = loadOrders();
    orders.add(order);
    saveOrders(orders);

    CartService.clearCart(user.getPhone());
  }

  public static void createFiles() {
    try {
      new File(ORDER_FILE_PATH).createNewFile();
    } catch (Exception e) {
      System.out.println("Failed to create order file: " + e.getMessage());
    }
  }

  public static ArrayList<Order> getOrders(String userPhone) {
    ArrayList<Order> orders = loadOrders();
    orders.removeIf(order -> !order.getUser().getPhone().equals(userPhone));
    return orders;
  }

  public static ArrayList<Order> getOrders() {
    return loadOrders();
  }

  public static double getOrdersTotalPrice() {
    ArrayList<Order> orders = loadOrders();
    double total = 0;
    for (Order order : orders) {
      total += order.getTotalPrice();
    }
    return total;
  }

  public static int getOrdersCount() {
    return loadOrders().size();
  }

  public static int getDeliveredOrdersCount() {
    ArrayList<Order> orders = loadOrders();
    int count = 0;
    for (Order order : orders) {
      if (order.isDelivered()) {
        count++;
      }
    }
    return count;
  }

  public static int getPendingOrdersCount() {
    return getOrdersCount() - getDeliveredOrdersCount();
  }

  private static int generateOrderID() {
    return getOrdersCount() > 0 ? loadOrders().get(loadOrders().size() - 1).getOrderID() + 1 : 1;
  }
}
