package com.group8.folwala.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

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
import javafx.scene.text.Text;

public class InventoryController {

  @FXML
  private FlowPane inventoryFlowPane;

  @FXML
  private Label totalProductsLabel;

  @FXML
  private TextField searchField;

  ArrayList<Product> products;

  private static EditProductController editProductController;

  @FXML
  public void initialize() {
    products = ProductService.getAllProducts();
    totalProductsLabel.setText("Total Products: " + products.size());
    loadAllProducts();
  }

  public static void setProductController(EditProductController editProductController) {
    InventoryController.editProductController = editProductController;
  }

  public void refreshProducts() {
    products = ProductService.getAllProducts();
    totalProductsLabel.setText("Total Products: " + products.size());
    loadAllProducts();
  }

  public void loadAllProducts() {
    inventoryFlowPane.getChildren().clear();
    // Collections.reverse(products);
    for (Product product : products) {
      HBox productBox = createProductBox(product);
      inventoryFlowPane.getChildren().add(productBox);
    }
  }

  private HBox createProductBox(Product product) {
    HBox productBox = new HBox();
    productBox.setAlignment(javafx.geometry.Pos.CENTER);
    productBox.setPrefWidth(390);
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
    infoBox1.prefWidthProperty().set(150);
    infoBox1.getChildren().addAll(nameLabel, categoryLabel);

    VBox infoBox2 = new VBox();
    infoBox2.prefWidthProperty().set(70);
    infoBox2.getChildren().addAll(unitLabel, priceLabel);

    Button editButton = new Button();
    editButton.setOnAction(e -> {
      SceneController.showProductEditStage();
      editProductController.setProduct(product);
      EditProductController.setInventoryController(this);
    });

    Button deleteButton = new Button();
    deleteButton.setOnAction(e -> {
      String message = "Are you sure you want to delete this product?\n" + product;
      AlertController.showConfirmation("Delete Product", message, () -> {
        ProductService.exportProducts();
        ProductService.deleteProduct(product);
        refreshProducts();
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

  @FXML
  public void visitAddProductsScene() {
    SceneController.setScene("AddProducts.fxml", "Add Products");
  }

  @FXML
  public void searchProducts() {
    String query = searchField.getText().toLowerCase();
    if (query.isEmpty()) {
      loadAllProducts();
      return;
    }

    ArrayList<Product> searchResults = new ArrayList<>();
    for (Product product : products) {
      if (product.getName().toLowerCase().contains(query) || product.getCategory().toLowerCase().contains(query)
          || product.getUnit().toLowerCase().contains(query) || String.valueOf(product.getPrice()).contains(query)
          || String.valueOf(product.getProductID()).contains(query)) {
        searchResults.add(product);
      }
    }

    inventoryFlowPane.getChildren().clear();
    for (Product product : searchResults) {
      HBox productBox = createProductBox(product);
      inventoryFlowPane.getChildren().add(productBox);
    }
  }

  @FXML
  public void exportProducts() {
    ProductService.exportProducts();
  }

}
