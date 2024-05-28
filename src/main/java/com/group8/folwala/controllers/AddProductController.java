package com.group8.folwala.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.group8.folwala.models.Product;
import com.group8.folwala.services.ProductService;

public class AddProductController {

  @FXML
  private Button uploadImageButton;

  @FXML
  private TextField nameField, priceField, quantityField, unitField;

  @FXML
  private ImageView productImageView;

  @FXML
  private Label errorLabel;

  @FXML
  private ChoiceBox<String> categoryChoiceBox;

  String[] categories = { "Fleshy Fruits", "Dry Fruits", "Fruit Seeds", "Fruit Juice", "Vegetables" };

  private File selectedImageFile, destFile;

  @FXML
  public void initialize() {
    categoryChoiceBox.getItems().addAll(categories);
  }

  @FXML
  private void handleUploadImage() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", "*.webp", "*.png", "*.jpg", "*.jpeg"));
    Stage stage = (Stage) uploadImageButton.getScene().getWindow();
    selectedImageFile = fileChooser.showOpenDialog(stage);

    if (selectedImageFile != null) {
      File destDir = new File("src/main/resources/images/products");
      if (!destDir.exists()) {
        destDir.mkdirs();
      }

      destFile = new File(destDir, selectedImageFile.getName());
      int counter = 1;
      String originalName = destFile.getName();
      while (destFile.exists()) {
        String newName = originalName.substring(0, originalName.lastIndexOf('.'))
            + "_" + counter
            + originalName.substring(originalName.lastIndexOf('.'));
        destFile = new File(destDir, newName);
        counter++;
      }

      Image image = new Image(selectedImageFile.toURI().toString());
      productImageView.setImage(image);

    }
  }

  @FXML
  private void handleAddProduct() {
    String name = nameField.getText();
    String price = priceField.getText();
    String quantity = quantityField.getText();
    String unit = unitField.getText();
    String category = categoryChoiceBox.getValue();

    if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || unit.isEmpty() || category == null) {
      errorLabel.setText("Please fill in all the fields");
      return;
    }

    if (selectedImageFile == null) {
      errorLabel.setText("Please upload an image");
      return;
    }

    try {
      Files.copy(selectedImageFile.toPath(), destFile.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }

    Product product = new Product(
        ProductService.generateProductID(),
        name,
        Double.parseDouble(price),
        Integer.parseInt(quantity),
        category,
        unit,
        false,
        destFile.getName());

    ProductService.saveProduct(product);
  }
}
