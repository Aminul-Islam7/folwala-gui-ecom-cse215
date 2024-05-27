package com.group8.folwala.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

import com.group8.folwala.models.Product;
import com.group8.folwala.services.ProductService;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

public class ProductController {

  @FXML
  private FlowPane productFlowPane;

  @FXML
  public void initialize() {
    // ObservableList<Product> products =
    // FXCollections.observableArrayList(Product.getAllProducts());

    // productList.getItems().addAll(products);

    // productList.getSelectionModel().selectedItemProperty().addListener(
    // (observable, oldValue, newValue) -> printProductId(newValue));

    loadProducts();
  }

  // public void printProductId(Product product) {
  // System.out.println(product.getName());
  // }

  private void loadProducts() {
    ArrayList<Product> products = ProductService.getAllProducts();

    for (Product product : products) {
      VBox productBox = createProductBox(product);
      productFlowPane.getChildren().add(productBox);
    }
  }

  private VBox createProductBox(Product product) {
    VBox productBox = new VBox();
    productBox.setAlignment(javafx.geometry.Pos.CENTER);
    productBox.setPrefWidth(235);
    productBox.setPrefHeight(330);
    productBox.setSpacing(1);
    productBox.getStyleClass().add("product-box");

    ImageView imageView = new ImageView("/images/products/" + product.getImage());
    imageView.setFitWidth(170);
    imageView.setFitHeight(170);
    imageView.preserveRatioProperty().set(true);

    Label nameLabel = new Label(product.getName());
    Label unitLabel = new Label(product.getUnit());
    Label priceLabel = new Label("৳ " + (int) product.getPrice());
    TextField quantityField = new TextField("0");

    quantityField.minHeightProperty().set(40);
    quantityField.alignmentProperty().set(javafx.geometry.Pos.CENTER);

    nameLabel.getStyleClass().add("name");
    unitLabel.getStyleClass().add("unit");
    priceLabel.getStyleClass().add("price");

    HBox quantityBox = new HBox(5);
    quantityBox.minHeightProperty().set(45);

    Button addButton = new Button();
    Button subtractButton = new Button();
    Button addToCartButton = new Button("Add to Cart");

    FontAwesomeIcon minusIcon = new FontAwesomeIcon();
    minusIcon.glyphNameProperty().set("MINUS");
    subtractButton.setGraphic(minusIcon);

    FontAwesomeIcon plusIcon = new FontAwesomeIcon();
    plusIcon.glyphNameProperty().set("PLUS");
    addButton.setGraphic(plusIcon);

    addButton.setMinWidth(50);
    addButton.setMinHeight(40);
    subtractButton.setMinWidth(50);
    subtractButton.setMinHeight(40);

    addToCartButton.prefWidthProperty().set(250);
    addToCartButton.getStyleClass().add("add-to-cart-btn");

    quantityBox.getChildren().addAll(subtractButton, quantityField, addButton);

    addButton.setOnAction(e -> {
      int quantity = Integer.parseInt(quantityField.getText());
      quantityField.setText(String.valueOf(++quantity));
    });

    subtractButton.setOnAction(e -> {
      int quantity = Integer.parseInt(quantityField.getText());
      if (quantity > 0) {
        quantityField.setText(String.valueOf(--quantity));
      }
    });

    addToCartButton.setOnAction(e -> {
      int quantity = Integer.parseInt(quantityField.getText());
      if (quantity > 0) {
        // cart.addItemToCart(product, quantity);
        quantityField.setText("0"); // Reset after adding to cart
        System.out.println(product.getName() + " added to cart with quantity " + quantity);
      }
    });

    productBox.getChildren().addAll(
        imageView,
        nameLabel,
        unitLabel,
        priceLabel,
        quantityBox,
        addToCartButton);

    return productBox;
  }

}
