package com.group8.folwala.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Product {
    private String name;
    private double price;
    private int productID;
    private int stockQuantity;
    private String category;
    private String unit;
    private boolean isDeleted;

    public Product(int productID, String name, double price, int stockQuantity, String category, String unit) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.unit = unit;
        this.isDeleted = false;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getProductID() {
        return productID;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return name + " - " + price + " - " + stockQuantity + " - " + category + " - " + unit;
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public static ArrayList<Product> getProductsByCategory(String category) {
        String filePath = "data/products.txt";
        ArrayList<Product> products = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(":");
                if (data[4].equals(category)) {
                    Product product = new Product(Integer.parseInt(
                            data[0]), data[1], Double.parseDouble(data[2]), Integer.parseInt(data[3]),
                            data[4], data[5]);
                    products.add(product);
                }
            }
            bufferedReader.close();
            return products;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Product> getAllProducts() {
        String filePath = "data/products.txt";
        ArrayList<Product> products = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(":");
                Product product = new Product(Integer.parseInt(
                        data[0]), data[1], Double.parseDouble(data[2]), Integer.parseInt(data[3]),
                        data[4], data[5]);
                products.add(product);
            }
            bufferedReader.close();
            return products;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void save() {
        String filePath = "data/products.txt";

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(productID + ":" + name + ":" + price + ":" + stockQuantity + ":" + category + ":"
                    + unit + ":" + false);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int generateProductID() {
        String filePath = "data/products.txt";
        int productID = 0;

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(":");
                productID = Integer.parseInt(data[0]);
            }
            bufferedReader.close();
            return productID + 1;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
