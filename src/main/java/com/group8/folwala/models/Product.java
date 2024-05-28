package com.group8.folwala.models;

import java.io.Serializable;

public class Product implements Serializable {

    private int productID;
    private String name;
    private String unit;
    private double price;
    private String category;
    private String image;

    public Product(
            int productID,
            String name,
            String unit,
            double price,
            String category,
            String image) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.category = category;
        this.unit = unit;
        this.image = image;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return productID + ". " + name + " (৳ " + price + " / " + unit + ") - " + category;
    }
}
