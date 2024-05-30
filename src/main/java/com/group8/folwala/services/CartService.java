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

  public static void removeItemFromCart(String userPhone, int productId) {
    Cart cart = loadCart(userPhone);
    cart.removeCartItem(productId);
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

  public static ArrayList<CartItem> getCartItems() {
    ArrayList<CartItem> cartItems = new ArrayList<>();
    File dir = new File(CART_FILE_PATH);
    if (dir.exists()) {
      for (File file : dir.listFiles()) {
        if (!file.getName().endsWith(".cart")) {
          continue;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
          Cart cart = (Cart) ois.readObject();
          for (CartItem item : cart.getCartItems()) {
            cartItems.add(item);
          }
        } catch (IOException | ClassNotFoundException e) {
          System.out.println("Failed to get cart items from file: " + file.getName() + " - " + e.getMessage());
          e.printStackTrace();
        }
      }
    }
    return cartItems;
  }
}
