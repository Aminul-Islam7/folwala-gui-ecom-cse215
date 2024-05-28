package com.group8.folwala.controllers;

import java.net.URL;
import java.util.ArrayList;

import com.group8.folwala.models.Product;
import com.group8.folwala.services.ProductService;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InventoryController {

  @FXML
  private FlowPane inventoryFlowPane;

  ArrayList<Product> products;

  @FXML
  public void initialize() {
    products = ProductService.getAllProducts();
    loadAllProducts();
  }

  public void loadAllProducts() {
    inventoryFlowPane.getChildren().clear();
    for (Product product : products) {
      HBox productBox = createProductBox(product);
      inventoryFlowPane.getChildren().add(productBox);
    }
  }

  private HBox createProductBox(Product product) {
    HBox productBox = new HBox();
    productBox.setAlignment(javafx.geometry.Pos.CENTER);
    productBox.setPrefWidth(385);
    // productBox.setPrefHeight(65);
    productBox.setSpacing(6);
    productBox.getStyleClass().add("inventory-item");

    URL imageURL = getClass().getResource("/images/products/" + product.getImage());
    if (imageURL == null)
      imageURL = getClass().getResource("/images/logo.png");

    ImageView imageView = new ImageView(imageURL.toString());

    imageView.setFitWidth(40);
    imageView.setFitHeight(40);
    imageView.preserveRatioProperty().set(true);

    Label nameLabel = new Label(product.getName());
    Label unitLabel = new Label(product.getUnit());
    Label priceLabel = new Label("৳ " + (int) product.getPrice());
    Label categoryLabel = new Label(product.getCategory());

    VBox infoBox1 = new VBox();
    infoBox1.prefWidthProperty().set(140);
    infoBox1.getChildren().addAll(nameLabel, categoryLabel);

    VBox infoBox2 = new VBox();
    infoBox2.prefWidthProperty().set(70);
    infoBox2.getChildren().addAll(unitLabel, priceLabel);

    Button editButton = new Button();
    editButton.setOnAction(e -> {
      SceneController.setScene("EditProduct.fxml", "Edit Product");
    });

    Button deleteButton = new Button();
    deleteButton.setOnAction(e -> {
      String message = "Are you sure you want to delete this product?\n" + product;
      AlertController.showConfirmation("Delete Product", message, () -> {
        ProductService.exportProducts();
        ProductService.deleteProduct(product);
        products = ProductService.getAllProducts();
        loadAllProducts();
      });
    });

    FontAwesomeIcon editIcon = new FontAwesomeIcon();
    editIcon.glyphNameProperty().set("PENCIL");
    editButton.setGraphic(editIcon);

    FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
    deleteIcon.glyphNameProperty().set("TRASH");
    deleteButton.setGraphic(deleteIcon);

    productBox.getChildren().addAll(imageView, infoBox1, infoBox2, editButton, deleteButton);

    return productBox;
  }

}
