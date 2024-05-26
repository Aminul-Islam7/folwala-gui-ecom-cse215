package com.group8.folwala.services;

import com.group8.folwala.models.Product;

import java.io.*;
import java.util.ArrayList;

public class ProductService {
  private static final String PRODUCT_FILE_PATH = "data/products.txt";

  public static ArrayList<Product> getProductsByCategory(String category) {
    ArrayList<Product> products = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE_PATH))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(":");
        if (data[4].equals(category)) {
          Product product = new Product(
              Integer.parseInt(data[0]),
              data[1],
              Double.parseDouble(data[2]),
              Integer.parseInt(data[3]),
              data[4],
              data[5],
              Boolean.parseBoolean(data[6]),
              data[7]);
          products.add(product);
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return products;
  }

  public static ArrayList<Product> getAllProducts() {
    ArrayList<Product> products = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE_PATH))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(":");
        Product product = new Product(
            Integer.parseInt(data[0]),
            data[1],
            Double.parseDouble(data[2]),
            Integer.parseInt(data[3]),
            data[4],
            data[5],
            Boolean.parseBoolean(data[6]),
            data[7]);
        products.add(product);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return products;
  }

  public static void saveProduct(Product product) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCT_FILE_PATH, true))) {
      bw.write(product.getProductID() + ":" + product.getName() + ":" + product.getPrice() + ":" +
          product.getStockQuantity() + ":" + product.getCategory() + ":" + product.getUnit() + ":" +
          product.isDeleted() + ":" + product.getImage());
      bw.newLine();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static int generateProductID() {
    int productID = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE_PATH))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(":");
        productID = Integer.parseInt(data[0]);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return productID + 1;
  }
}
