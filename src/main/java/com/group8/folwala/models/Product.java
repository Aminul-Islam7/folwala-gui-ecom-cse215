package com.group8.folwala.models;

public class Product {
    private String name;
    private double price;
    private int productID;
    private int stockQuantity;
    private String category;
    private String unit;
    private boolean isDeleted;
    private String image;

    public Product(
            int productID,
            String name,
            double price,
            int stockQuantity,
            String category,
            String unit,
            boolean isDeleted,
            String image) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.unit = unit;
        this.isDeleted = false;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return name + " - " + price + " - " + stockQuantity + " - " + category + " - " + unit;
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }
}
