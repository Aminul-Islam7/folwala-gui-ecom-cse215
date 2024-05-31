package com.group8.folwala.services;

import com.group8.folwala.models.Cart;
import com.group8.folwala.models.CartItem;
import com.group8.folwala.models.Product;

import java.io.*;
import java.util.ArrayList;

public class CartService {

  private static final String CART_FILE_PATH = "src/main/resources/data/carts/";

  public static Cart loadCart(String userPhone) {
    Cart loadedCart = new Cart(userPhone);
    File file = new File(CART_FILE_PATH + userPhone + ".cart");
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        loadedCart = (Cart) ois.readObject();
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("No cart found or failed to load cart for user " + userPhone + ": " + e.getMessage());
        e.printStackTrace();
      }
    }
    return loadedCart;
  }

  public static void saveCart(Cart cart) {
    try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream(CART_FILE_PATH + cart.getUserPhone() + ".cart"))) {
      oos.writeObject(cart);
    } catch (IOException e) {
      System.out.println("Failed to save cart for user " + cart.getUserPhone() + ": " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void addItemToCart(String userPhone, Product product, int quantity) {
    Cart cart = loadCart(userPhone);
    CartItem cartItem = new CartItem(product, quantity);
    cart.addCartItem(cartItem);
    saveCart(cart);
  }

  public static void clearCart(String userPhone) {
    Cart cart = new Cart(userPhone);
    saveCart(cart);
  }

  public static void createCart(String userPhone) {
    Cart cart = new Cart(userPhone);
    saveCart(cart);
  }

  public static void createFiles() {
    try {
      new File(CART_FILE_PATH).mkdirs();
    } catch (Exception e) {
      System.out.println("Failed to create cart files: " + e.getMessage());
    }
  }

  public static ArrayList<CartItem> getCartItems(String userPhone) {
    return loadCart(userPhone).getCartItems();
  }

  public static int getCartItemCount(String userPhone) {
    ArrayList<CartItem> cartItems = getCartItems(userPhone);
    int count = 0;
    for (CartItem cartItem : cartItems) {
      count += cartItem.getQuantity();
    }
    return count;
  }

  public static double getCartTotalPrice(String userPhone) {
    ArrayList<CartItem> cartItems = getCartItems(userPhone);
    double total = 0;
    for (CartItem cartItem : cartItems) {
      total += cartItem.getTotalPrice();
    }
    return total;
  }
}
