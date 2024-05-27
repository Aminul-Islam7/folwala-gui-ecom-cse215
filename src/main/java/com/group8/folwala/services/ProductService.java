package com.group8.folwala.services;

import com.group8.folwala.models.Product;

import java.io.*;
import java.util.ArrayList;

public class ProductService {
  private static final String PRODUCT_FILE_PATH = "data/products.dat";

  public static ArrayList<Product> getProductsByCategory(String category) {
    ArrayList<Product> products = new ArrayList<>();

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCT_FILE_PATH))) {
      products = (ArrayList<Product>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Failed to load products: " + e.getMessage());
    }

    ArrayList<Product> filteredProducts = new ArrayList<>();
    for (Product product : products) {
      if (product.getCategory().equals(category)) {
        filteredProducts.add(product);
      }
    }
    return filteredProducts;
  }

  public static ArrayList<Product> getAllProducts() {
    ArrayList<Product> products = new ArrayList<>();

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCT_FILE_PATH))) {
      products = (ArrayList<Product>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Failed to load products: " + e.getMessage());
    }
    return products;
  }

  public static void saveProduct(Product product) {
    ArrayList<Product> products = getAllProducts();
    products.add(product);

    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCT_FILE_PATH))) {
      oos.writeObject(products);
    } catch (IOException e) {
      System.out.println("Failed to save product: " + e.getMessage());
    }
  }

  public static int generateProductID() {
    ArrayList<Product> products = getAllProducts();
    int productID = products.size() > 0 ? products.get(products.size() - 1).getProductID() + 1 : 1;
    return productID;
  }
}
